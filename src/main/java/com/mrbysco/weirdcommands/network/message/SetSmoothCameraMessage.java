package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

public class SetSmoothCameraMessage {
	private final boolean enabled;

	public SetSmoothCameraMessage(boolean enabled) {
		this.enabled = enabled;
	}

	public static SetSmoothCameraMessage decode(final FriendlyByteBuf buffer) {
		return new SetSmoothCameraMessage(buffer.readBoolean());
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeBoolean(enabled);
	}

	public void handle(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				Minecraft minecraft = Minecraft.getInstance();
				minecraft.options.smoothCamera = enabled;
			}
		});
		ctx.setPacketHandled(true);
	}
}
