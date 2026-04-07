package com.captcha.plugin.globals;

import com.captcha.plugin.CaptchaAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {
	public static final NamespacedKey CAPTCHA = createKey("captcha_item");

	private static NamespacedKey createKey(String value) {
		return new NamespacedKey(JavaPlugin.getPlugin(CaptchaAPI.class), value);
	}
}
