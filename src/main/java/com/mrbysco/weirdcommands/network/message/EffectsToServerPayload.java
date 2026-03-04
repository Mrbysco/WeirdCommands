package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.List;

public record EffectsToServerPayload(List<Identifier> values) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, EffectsToServerPayload> CODEC = StreamCodec.composite(
			Identifier.STREAM_CODEC.apply(ByteBufCodecs.list()),
			EffectsToServerPayload::values,
			EffectsToServerPayload::new);
	public static final Type<EffectsToServerPayload> ID = new Type<>(WeirdCommandsMod.modLoc("effects_to_server"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
