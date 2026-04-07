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

	public User(UUID uuid) {
		this.uuid = uuid;
	}
}
