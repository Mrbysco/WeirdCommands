package com.mrbysco.weirdcommands.commands;

import javax.annotation.Nullable;

public enum Perspective {
	FIRST_PERSON("first_person"),
	THIRD_PERSON_BACK("third_person_back"),
	THIRD_PERSON_FRONT("third_person_front");

	private final String name;

	Perspective(String name) {
		this.name = name;
	}

	public String getPerspectiveName() {
		return name;
	}

	@Nullable
	public static Perspective getByName(@Nullable String value) {
		for (Perspective captcha : values()) {
			if (captcha.getPerspectiveName().equals(value)) {
				return captcha;
			}
		}
		return FIRST_PERSON;
	}
}
