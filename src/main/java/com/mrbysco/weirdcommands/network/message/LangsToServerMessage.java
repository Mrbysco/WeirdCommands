package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.commands.ModCommands;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.List;

public class LangsToServerMessage {
	private final List<String> values;

	public LangsToServerMessage(List<String> values) {
		this.values = values;
	}

	public static LangsToServerMessage decode(final FriendlyByteBuf buffer) {
		List<String> values = buffer.readList(FriendlyByteBuf::readUtf);
		return new LangsToServerMessage(values);
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeCollection(this.values, FriendlyByteBuf::writeUtf);
	}

	public void handle(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isServer()) {
				if (values.isEmpty()) {
					ModCommands.languages.clear();
					ModCommands.languages.add("en_us");
				} else {
					ModCommands.languages = this.values;
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
