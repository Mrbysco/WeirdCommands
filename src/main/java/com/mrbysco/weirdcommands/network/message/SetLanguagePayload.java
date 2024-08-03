package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SetLanguagePayload(String language) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetLanguagePayload> CODEC = CustomPacketPayload.codec(
			SetLanguagePayload::write,
			SetLanguagePayload::new);
	public static final Type<SetLanguagePayload> ID = new Type<>(WeirdCommandsMod.modLoc("set_language"));

	public SetLanguagePayload(final FriendlyByteBuf buffer) {
		this(buffer.readUtf());
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeUtf(language);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
