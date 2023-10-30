package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

public class SetLanguageMessage {
    private final String language;

    public SetLanguageMessage(String language) {
        this.language = language;
    }

    public static SetLanguageMessage decode(final FriendlyByteBuf buffer) {
        return new SetLanguageMessage(buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(language);
    }

    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide().isClient()) {
                Minecraft minecraft = Minecraft.getInstance();
                LanguageManager languageManager = minecraft.getLanguageManager();
                LanguageInfo languageInfo = languageManager.getLanguage(language);
                if (languageInfo != null) {
                    languageManager.setSelected(language);
                }
                minecraft.reloadResourcePacks();
            }
        });
        ctx.setPacketHandled(true);
    }
}
