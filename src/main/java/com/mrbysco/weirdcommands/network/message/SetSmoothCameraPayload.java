package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetSmoothCameraPayload(boolean enabled) implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(WeirdCommandsMod.MOD_ID, "set_smooth_camera");

	public SetSmoothCameraPayload(final FriendlyByteBuf buffer) {
		this(buffer.readBoolean());
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeBoolean(enabled);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}
