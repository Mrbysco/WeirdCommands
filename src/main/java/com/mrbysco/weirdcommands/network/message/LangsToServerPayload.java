package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public record LangsToServerPayload(List<String> values) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, LangsToServerPayload> CODEC = CustomPacketPayload.codec(
			LangsToServerPayload::write,
			LangsToServerPayload::new);
	public static final Type<LangsToServerPayload> ID = new Type<>(WeirdCommandsMod.modLoc("langs_to_server"));

	public LangsToServerPayload(final FriendlyByteBuf buffer) {
		this(buffer.readList(FriendlyByteBuf::readUtf));
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeCollection(this.values, FriendlyByteBuf::writeUtf);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
