package com.captcha.plugin.config.keys;

import com.captcha.plugin.config.Config;
import com.captcha.plugin.config.DataSupplier;
import org.bukkit.ChatColor;

public class TextConfigKey extends ConfigKey<String> {
	public TextConfigKey(String path) {
		super(DataSupplier.STRING, path);
	}

	@Override
	public String get(Config config) {
		String s = super.get(config);

		return ChatColor.translateAlternateColorCodes('&', s == null ? "" : s);
	}
}
