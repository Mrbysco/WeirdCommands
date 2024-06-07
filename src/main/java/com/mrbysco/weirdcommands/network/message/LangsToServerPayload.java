package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record LangsToServerPayload(List<String> values) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, LangsToServerPayload> CODEC = CustomPacketPayload.codec(
			LangsToServerPayload::write,
			LangsToServerPayload::new);
	public static final Type<LangsToServerPayload> ID = CustomPacketPayload.createType(new ResourceLocation(WeirdCommandsMod.MOD_ID, "langs_to_server").toString());

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
