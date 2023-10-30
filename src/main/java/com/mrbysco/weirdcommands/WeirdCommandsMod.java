package com.mrbysco.weirdcommands;

import com.mojang.logging.LogUtils;
import com.mrbysco.weirdcommands.commands.ModCommands;
import com.mrbysco.weirdcommands.network.PacketHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod(WeirdCommandsMod.MOD_ID)
public class WeirdCommandsMod {
    public static final String MOD_ID = "weirdcommands";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WeirdCommandsMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        NeoForge.EVENT_BUS.addListener(this::onCommandRegister);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    public void onCommandRegister(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }
}
