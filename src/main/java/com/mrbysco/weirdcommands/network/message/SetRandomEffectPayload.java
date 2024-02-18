package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetRandomEffectPayload() implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(WeirdCommandsMod.MOD_ID, "set_random_effect");

	public SetRandomEffectPayload(final FriendlyByteBuf buffer) {
		this();
	}

	public void write(FriendlyByteBuf buffer) {

	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}
