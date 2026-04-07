package com.captcha.plugin.globals;

import com.captcha.plugin.config.keys.TextConfigKey;

import static com.captcha.plugin.config.DataSupplier.textKey;

public final class Messages {
	// generic
	public static final TextConfigKey BLOCKED_CONSOLE = textKey("blocked_console");
	public static final TextConfigKey BLOCKED_PERMISSION = textKey("blocked_permission");
	public static final TextConfigKey BLOCKED_CAPTCHA = textKey("blocked_captcha");

	// commands
	public static final TextConfigKey COMMAND_CAPTCHA_USAGE = textKey("commands.captcha.usage");
	public static final TextConfigKey COMMAND_CAPTCHA_SHOW = textKey("commands.captcha.show");
	public static final TextConfigKey COMMAND_CAPTCHA_NO_PLAYER = textKey("commands.captcha.no_player");
	public static final TextConfigKey COMMAND_CAPTCHA_CORRECT = textKey("commands.captcha.correct");
	public static final TextConfigKey COMMAND_CAPTCHA_WRONG = textKey("commands.captcha.wrong");
}
