package com.captcha.plugin.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class User {

	@Getter
	private final UUID uuid;

	@Getter
	@Setter
	private boolean inCaptcha = true; /* disabled when they pass */

	@Getter
	@Setter
	private int attempts = 3;

	public User(UUID uuid) {
		this.uuid = uuid;
	}
}
