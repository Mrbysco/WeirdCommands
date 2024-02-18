package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record EffectsToServerPayload(List<ResourceLocation> values) implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(WeirdCommandsMod.MOD_ID, "effects_to_server");

	public EffectsToServerPayload(final FriendlyByteBuf buffer) {
		this(buffer.readList(FriendlyByteBuf::readResourceLocation));
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeCollection(this.values, FriendlyByteBuf::writeResourceLocation);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}
