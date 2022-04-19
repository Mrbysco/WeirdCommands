package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.commands.ModCommands;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.List;
import java.util.function.Supplier;

public class EffectsToServerMessage {
	private final List<ResourceLocation> values;

	public EffectsToServerMessage(List<ResourceLocation> values) {
		this.values = values;
	}

	public static EffectsToServerMessage decode(final FriendlyByteBuf buffer) {
		List<ResourceLocation> values = buffer.readList((byteBuffer) -> byteBuffer.readResourceLocation());
		return new EffectsToServerMessage(values);
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeCollection(this.values, (byteBuffer, value) -> byteBuffer.writeResourceLocation(value));
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isServer()) {
				ModCommands.effects.clear();
				if (!values.isEmpty()) {
					ModCommands.effects = this.values;
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
