package com.mrbysco.weirdcommands.commands;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public enum Perspective {
	FIRST_PERSON("first_person"), THIRD_PERSON_BACK("third_person_back"), THIRD_PERSON_FRONT("third_person_front");


	public static final StreamCodec<FriendlyByteBuf, Perspective> PERSPECTIVE_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			Perspective::getPerspectiveName,
			Perspective::getByName);

	private final String name;

	Perspective(String name) {
		this.name = name;
	}

	public String getPerspectiveName() {
		return name;
	}

	@NotNull
	public static Perspective getByName(@Nullable String value) {
		if (value != null) {
			for (Perspective perspective : values()) {
				if (perspective.getPerspectiveName().equals(value)) {
					return perspective;
				}
			}
		}
		return FIRST_PERSON;
	}
}
