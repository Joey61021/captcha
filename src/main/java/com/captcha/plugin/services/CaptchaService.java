package com.captcha.plugin.services;

import com.captcha.plugin.builders.ItemBuilder;
import com.captcha.plugin.builders.MenuBuilder;
import com.captcha.plugin.globals.Keys;
import com.captcha.plugin.user.User;
import com.captcha.plugin.user.UserManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CaptchaService {

	private final List<CaptchaItem> items = new ArrayList<>();

	public CaptchaService() {
		items.add(new CaptchaItem(Material.ORANGE_STAINED_GLASS_PANE, "&6Orange"));
		items.add(new CaptchaItem(Material.MAGENTA_STAINED_GLASS_PANE, "&dPink"));
		items.add(new CaptchaItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bBlue"));
		items.add(new CaptchaItem(Material.YELLOW_STAINED_GLASS_PANE, "&eYellow"));
		items.add(new CaptchaItem(Material.LIME_STAINED_GLASS_PANE, "&aLime"));
		items.add(new CaptchaItem(Material.RED_STAINED_GLASS_PANE, "&cRed"));
	}

	public void open(Player player) {
		gui(player);

		User user = UserManager.getUser(player);
		user.setInCaptcha(true);
		user.setAttempts(3);
	}

	public void gui(Player player) {
		CaptchaItem c = items.get(rand(0, items.size()-1)); /* correct color is predetermined */
		Inventory inv = new MenuBuilder("Click on the color " + c.getName(), 3).fill().build();
		populate(inv, c.getMaterial());

		ItemBuilder i = new ItemBuilder(c.getMaterial());
		i.setDisplayName("&aClick me!");
		i.addPersistentContainer(Keys.CAPTCHA, PersistentDataType.BOOLEAN, true);

		inv.setItem(rand(0, inv.getSize()-1), i.build());
		player.openInventory(inv);
	}

	public void populate(Inventory inv, Material m) {
		for (int i = 0; i < items.size(); i++) {
			CaptchaItem c = items.get(i);
			if (!c.getMaterial().equals(m)) {
				ItemBuilder item = new ItemBuilder(c.getMaterial());
				item.setDisplayName("&cDon't click me!");
				inv.setItem(rand(0, inv.getSize()-1), item.build());
			}
		}
	}

	private int rand(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	private class CaptchaItem {
		@Getter private Material material;
		@Getter private String name;

		public CaptchaItem(Material material, String name) {
			this.material = material;
			this.name = name;
		}
	}
}
