package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.commands.ModCommands;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.List;

public class EffectsToServerMessage {
	private final List<ResourceLocation> values;

	public EffectsToServerMessage(List<ResourceLocation> values) {
		this.values = values;
	}

	public static EffectsToServerMessage decode(final FriendlyByteBuf buffer) {
		List<ResourceLocation> values = buffer.readList(FriendlyByteBuf::readResourceLocation);
		return new EffectsToServerMessage(values);
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeCollection(this.values, FriendlyByteBuf::writeResourceLocation);
	}

	public void handle(NetworkEvent.Context ctx) {
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
