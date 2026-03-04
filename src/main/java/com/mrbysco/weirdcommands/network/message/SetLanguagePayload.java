package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SetLanguagePayload(String language) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetLanguagePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			SetLanguagePayload::language,
			SetLanguagePayload::new);
	public static final Type<SetLanguagePayload> ID = new Type<>(WeirdCommandsMod.modLoc("set_language"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
