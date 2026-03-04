package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import com.mrbysco.weirdcommands.commands.Perspective;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SetPerspectivePayload(Perspective perspective) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetPerspectivePayload> CODEC = StreamCodec.composite(
			Perspective.PERSPECTIVE_CODEC,
			SetPerspectivePayload::perspective,
			SetPerspectivePayload::new);
	public static final Type<SetPerspectivePayload> ID = new Type<>(WeirdCommandsMod.modLoc("set_perspective"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
