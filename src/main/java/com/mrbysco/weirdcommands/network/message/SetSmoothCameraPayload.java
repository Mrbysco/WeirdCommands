package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SetSmoothCameraPayload(boolean enabled) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetSmoothCameraPayload> CODEC = CustomPacketPayload.codec(
			SetSmoothCameraPayload::write,
			SetSmoothCameraPayload::new);
	public static final Type<SetSmoothCameraPayload> ID = new Type<>(WeirdCommandsMod.modLoc("set_smooth_camera"));

	public SetSmoothCameraPayload(final FriendlyByteBuf buffer) {
		this(buffer.readBoolean());
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeBoolean(enabled);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
