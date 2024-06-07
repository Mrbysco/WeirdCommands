package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetRandomEffectPayload() implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetRandomEffectPayload> CODEC = CustomPacketPayload.codec(
			SetRandomEffectPayload::write,
			SetRandomEffectPayload::new);
	public static final Type<SetRandomEffectPayload> ID = CustomPacketPayload.createType(new ResourceLocation(WeirdCommandsMod.MOD_ID, "set_random_effect").toString());

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
