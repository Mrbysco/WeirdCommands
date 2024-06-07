package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import com.mrbysco.weirdcommands.commands.Perspective;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetPerspectivePayload(Perspective perspective) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetPerspectivePayload> CODEC = CustomPacketPayload.codec(
			SetPerspectivePayload::write,
			SetPerspectivePayload::new);
	public static final Type<SetPerspectivePayload> ID = CustomPacketPayload.createType(new ResourceLocation(WeirdCommandsMod.MOD_ID, "set_perspective").toString());

	public SetPerspectivePayload(final FriendlyByteBuf buffer) {
		this(Perspective.getByName(buffer.readUtf()));
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeUtf(perspective.getPerspectiveName());
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
