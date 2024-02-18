package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetLanguagePayload(String language) implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(WeirdCommandsMod.MOD_ID, "set_language");

	public SetLanguagePayload(final FriendlyByteBuf buffer) {
		this(buffer.readUtf());
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeUtf(language);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}
