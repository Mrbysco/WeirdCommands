package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkEvent;

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

	public void handle(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				Minecraft minecraft = Minecraft.getInstance();
				if (effect == null) {
					minecraft.gameRenderer.shutdownEffect();
				} else {
					minecraft.gameRenderer.loadEffect(effect);
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
