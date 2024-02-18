package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetEffectPayload(ResourceLocation effect) implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(WeirdCommandsMod.MOD_ID, "set_effect");

	public SetEffectPayload(final FriendlyByteBuf buffer) {
		this(getEffect(buffer));
	}

	public static ResourceLocation getEffect(FriendlyByteBuf buffer) {
		String effectID = buffer.readUtf();
		return effectID.isEmpty() ? null : ResourceLocation.tryParse(effectID);
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeUtf(effect != null ? effect.toString() : "");
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}
