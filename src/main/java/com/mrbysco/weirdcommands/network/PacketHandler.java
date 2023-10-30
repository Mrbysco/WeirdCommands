package com.mrbysco.weirdcommands.network;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import com.mrbysco.weirdcommands.network.message.EffectsToServerMessage;
import com.mrbysco.weirdcommands.network.message.LangsToServerMessage;
import com.mrbysco.weirdcommands.network.message.SetEffectMessage;
import com.mrbysco.weirdcommands.network.message.SetLanguageMessage;
import com.mrbysco.weirdcommands.network.message.SetPerspectiveMessage;
import com.mrbysco.weirdcommands.network.message.SetRandomEffectMessage;
import com.mrbysco.weirdcommands.network.message.SetSmoothCameraMessage;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(WeirdCommandsMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void init() {
        CHANNEL.registerMessage(id++, LangsToServerMessage.class, LangsToServerMessage::encode, LangsToServerMessage::decode, LangsToServerMessage::handle);
        CHANNEL.registerMessage(id++, SetLanguageMessage.class, SetLanguageMessage::encode, SetLanguageMessage::decode, SetLanguageMessage::handle);
        CHANNEL.registerMessage(id++, EffectsToServerMessage.class, EffectsToServerMessage::encode, EffectsToServerMessage::decode, EffectsToServerMessage::handle);
        CHANNEL.registerMessage(id++, SetEffectMessage.class, SetEffectMessage::encode, SetEffectMessage::decode, SetEffectMessage::handle);
        CHANNEL.registerMessage(id++, SetRandomEffectMessage.class, SetRandomEffectMessage::encode, SetRandomEffectMessage::decode, SetRandomEffectMessage::handle);
        CHANNEL.registerMessage(id++, SetPerspectiveMessage.class, SetPerspectiveMessage::encode, SetPerspectiveMessage::decode, SetPerspectiveMessage::handle);
        CHANNEL.registerMessage(id++, SetSmoothCameraMessage.class, SetSmoothCameraMessage::encode, SetSmoothCameraMessage::decode, SetSmoothCameraMessage::handle);
    }
}
