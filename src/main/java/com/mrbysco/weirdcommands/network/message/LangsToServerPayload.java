package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public record LangsToServerPayload(List<String> values) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, LangsToServerPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
			LangsToServerPayload::values,
			LangsToServerPayload::new);
	public static final Type<LangsToServerPayload> ID = new Type<>(WeirdCommandsMod.modLoc("langs_to_server"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
