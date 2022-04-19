package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

public class SetEffectMessage {
	private final ResourceLocation effect;

	public SetEffectMessage(ResourceLocation effect) {
		this.effect = effect;
	}

	public static SetEffectMessage decode(final FriendlyByteBuf buffer) {
		String effectID = buffer.readUtf();
		return new SetEffectMessage(effectID.isEmpty() ? null : ResourceLocation.tryParse(effectID));
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUtf(effect != null ? effect.toString() : "");
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				EffectEvent.setEvent(this.effect).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class EffectEvent {
		private static SafeRunnable setEvent(ResourceLocation effect) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					Minecraft minecraft = Minecraft.getInstance();
					if (effect == null) {
						minecraft.gameRenderer.shutdownEffect();
					} else {
						minecraft.gameRenderer.loadEffect(effect);
					}
				}
			};
		}
	}
}
