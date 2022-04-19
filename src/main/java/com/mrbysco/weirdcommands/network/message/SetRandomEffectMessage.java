package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

public class SetRandomEffectMessage {

	public SetRandomEffectMessage() {
	}

	public static SetRandomEffectMessage decode(final FriendlyByteBuf buffer) {
		return new SetRandomEffectMessage();
	}

	public void encode(FriendlyByteBuf buffer) {

	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				EffectEvent.randomizeEffect().run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class EffectEvent {
		private static SafeRunnable randomizeEffect() {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					Minecraft minecraft = Minecraft.getInstance();
					minecraft.gameRenderer.cycleEffect();

					if (minecraft.gameRenderer.currentEffect() != null && minecraft.gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/blur.json")) {
						minecraft.gameRenderer.cycleEffect();

						if (minecraft.gameRenderer.currentEffect() != null && minecraft.gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/blur.json")) {
							minecraft.gameRenderer.shutdownEffect();
						}
					}
				}
			};
		}
	}
}
