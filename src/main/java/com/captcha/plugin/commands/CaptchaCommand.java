package com.captcha.plugin.commands;

import com.captcha.plugin.CaptchaAPI;
import com.captcha.plugin.config.Config;
import com.captcha.plugin.globals.Messages;
import com.captcha.plugin.globals.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CaptchaCommand extends Command {

	private final Config messages;

	public CaptchaCommand(Config messages) {
		super("captcha");

		this.messages = messages;

		setPermission(Permissions.COMMAND_CAPTCHA);
		setPermissionMessage(messages.get(Messages.BLOCKED_PERMISSION));
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String @NotNull [] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(messages.get(Messages.BLOCKED_CONSOLE));
			return false;
		}

		/* /gang <player> */
		if (args.length == 0) {
			player.sendMessage(messages.get(Messages.COMMAND_CAPTCHA_USAGE));
			return false;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(messages.get(Messages.COMMAND_CAPTCHA_NO_PLAYER));
			return false;
		}

		CaptchaAPI.getCaptchaService().open(target);
		player.sendMessage(messages.get(Messages.COMMAND_CAPTCHA_SHOW).replace("%player%", target.getName()));
		return false;
	}
}
