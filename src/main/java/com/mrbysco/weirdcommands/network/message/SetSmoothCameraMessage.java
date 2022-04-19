package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

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

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				UpdateEvent.updateSmoothCamera(this.enabled).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class UpdateEvent {
		private static SafeRunnable updateSmoothCamera(boolean enabled) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					Minecraft minecraft = Minecraft.getInstance();
					minecraft.options.smoothCamera = enabled;
				}
			};
		}
	}
}
