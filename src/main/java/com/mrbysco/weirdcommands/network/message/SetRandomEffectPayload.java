package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SetRandomEffectPayload() implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetRandomEffectPayload> CODEC = CustomPacketPayload.codec(
			SetRandomEffectPayload::write,
			SetRandomEffectPayload::new);
	public static final Type<SetRandomEffectPayload> ID = new Type<>(WeirdCommandsMod.modLoc("set_random_effect"));

	public SetRandomEffectPayload(final FriendlyByteBuf buffer) {
		this();
	}

	public void write(FriendlyByteBuf buffer) {

	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
