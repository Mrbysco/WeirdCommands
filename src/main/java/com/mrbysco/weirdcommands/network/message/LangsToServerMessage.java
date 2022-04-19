package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.commands.ModCommands;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.List;
import java.util.function.Supplier;

public class LangsToServerMessage {
	private final List<String> values;

	public LangsToServerMessage(List<String> values) {
		this.values = values;
	}

	public static LangsToServerMessage decode(final FriendlyByteBuf buffer) {
		List<String> values = buffer.readList((byteBuffer) -> byteBuffer.readUtf());
		return new LangsToServerMessage(values);
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeCollection(this.values, (byteBuffer, value) -> byteBuffer.writeUtf(value));
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
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
