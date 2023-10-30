package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.Objects;

public class SetRandomEffectMessage {

    public SetRandomEffectMessage() {
    }

    public static SetRandomEffectMessage decode(final FriendlyByteBuf buffer) {
        return new SetRandomEffectMessage();
    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide().isClient()) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.gameRenderer.cycleEffect();

                if (minecraft.gameRenderer.currentEffect() != null && Objects.requireNonNull(minecraft.gameRenderer.currentEffect()).getName().equals("minecraft:shaders/post/blur.json")) {
                    minecraft.gameRenderer.cycleEffect();

                    if (minecraft.gameRenderer.currentEffect() != null && Objects.requireNonNull(minecraft.gameRenderer.currentEffect()).getName().equals("minecraft:shaders/post/blur.json")) {
                        minecraft.gameRenderer.shutdownEffect();
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
