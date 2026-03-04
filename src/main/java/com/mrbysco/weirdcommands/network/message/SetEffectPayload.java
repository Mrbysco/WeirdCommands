package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public record SetEffectPayload(Optional<Identifier> effect) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetEffectPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.optional(Identifier.STREAM_CODEC),
			SetEffectPayload::effect,
			SetEffectPayload::new);
	public static final Type<SetEffectPayload> ID = new Type<>(WeirdCommandsMod.modLoc("set_effect"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
