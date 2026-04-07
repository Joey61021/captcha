package com.captcha.plugin.events;

import com.captcha.plugin.CaptchaAPI;
import com.captcha.plugin.config.Config;
import com.captcha.plugin.globals.Keys;
import com.captcha.plugin.globals.Messages;
import com.captcha.plugin.user.User;
import com.captcha.plugin.user.UserManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

@RequiredArgsConstructor
public class CaptchaListener implements Listener {

	@NonNull
	private final Config messages;

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPlayedBefore()) {
			return; /* todo -- config to disable or enable this check */
		}

		CaptchaAPI.getCaptchaService().open(player);
	}

	@EventHandler
	private void onClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if (UserManager.getUser(player.getUniqueId()).isInCaptcha()) {
			player.kickPlayer(CaptchaAPI.color("&cCaptcha failed, please try again"));
		}
	}

	private void handleBlockedEvent(Player player, Cancellable event) {
		if (UserManager.getUser(player.getUniqueId()).isInCaptcha()) {
			event.setCancelled(true);
			player.sendMessage(messages.get(Messages.BLOCKED_CAPTCHA));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
		}
	}

	@EventHandler
	private void onCommand(PlayerChatEvent event) {
		handleBlockedEvent(event.getPlayer(), event);
	}

	@EventHandler
	private void onBreak(BlockBreakEvent event) {
		handleBlockedEvent(event.getPlayer(), event);
	}

	@EventHandler
	private void onPlace(BlockPlaceEvent event) {
		handleBlockedEvent(event.getPlayer(), event);
	}

	@EventHandler
	private void onBreak(PlayerCommandPreprocessEvent event) {
		handleBlockedEvent(event.getPlayer(), event);
	}

	@EventHandler
	private void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = event.getView().getTitle();

		if (item == null || item.getType() == Material.AIR) {
			return;
		}

		if (UserManager.getUser(player.getUniqueId()).isInCaptcha() && title.startsWith("Click on the color ")) {
			event.setCancelled(true);

			if (item.getPersistentDataContainer().has(Keys.CAPTCHA, PersistentDataType.BOOLEAN)) {
				UserManager.getUser(player.getUniqueId()).setInCaptcha(false);
				player.sendMessage(messages.get(Messages.COMMAND_CAPTCHA_CORRECT));
				player.closeInventory();
				return;
			}

			User user = UserManager.getUser(player.getUniqueId());
			if (user.getAttempts() <= 1) {
				player.kickPlayer(CaptchaAPI.color("&cCaptcha failed, please try again"));
				return;
			}

			user.setAttempts(user.getAttempts() - 1);
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			player.sendMessage(messages.get(Messages.COMMAND_CAPTCHA_WRONG).replace("%num%", String.valueOf(user.getAttempts())));
		}
	}
}
