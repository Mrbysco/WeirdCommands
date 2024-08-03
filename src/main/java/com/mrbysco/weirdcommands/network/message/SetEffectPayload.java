package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetEffectPayload(ResourceLocation effect) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetEffectPayload> CODEC = CustomPacketPayload.codec(
			SetEffectPayload::write,
			SetEffectPayload::new);
	public static final Type<SetEffectPayload> ID = new Type<>(WeirdCommandsMod.modLoc("set_effect"));

	public SetEffectPayload(final FriendlyByteBuf buffer) {
		this(getEffect(buffer));
	}

	public static ResourceLocation getEffect(FriendlyByteBuf buffer) {
		String effectID = buffer.readUtf();
		return effectID.isEmpty() ? null : ResourceLocation.tryParse(effectID);
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeUtf(effect != null ? effect.toString() : "");
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
