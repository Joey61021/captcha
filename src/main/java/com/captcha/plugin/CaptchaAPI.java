package com.captcha.plugin;

import com.captcha.plugin.commands.CaptchaCommand;
import com.captcha.plugin.config.Config;
import com.captcha.plugin.globals.Messages;
import com.captcha.plugin.services.CaptchaService;
import com.captcha.plugin.user.UserManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CaptchaAPI extends JavaPlugin {
	/* configs */
	@Getter private static Config messages;

	/* services */
	@Getter private static CaptchaService captchaService;

	void registerEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	void registerEvents() {
		registerEvent(new UserManager());

	}

	void registerCommands() {
		getServer().getCommandMap().registerAll("captcha", List.of(
				new CaptchaCommand(messages)
		));
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onEnable() {
		messages = new Config(this, Messages.class, "messages.yml");

		/* services */
		captchaService = new CaptchaService();

		registerEvents();
		registerCommands();

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");



		Bukkit.getServicesManager().register(CaptchaAPI.class, this, this, ServicePriority.Normal);
	}

	@Override
	public void onDisable() {}

	public static void reloadConfigs() {
		messages.refresh();
	}

	public static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
