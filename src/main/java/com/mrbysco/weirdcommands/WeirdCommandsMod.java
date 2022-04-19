package com.mrbysco.weirdcommands;

import com.mojang.logging.LogUtils;
import com.mrbysco.weirdcommands.client.ClientHandler;
import com.mrbysco.weirdcommands.commands.ModCommands;
import com.mrbysco.weirdcommands.network.PacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(WeirdCommandsMod.MOD_ID)
public class WeirdCommandsMod {
	public static final String MOD_ID = "weirdcommands";
	public static final Logger LOGGER = LogUtils.getLogger();

	public WeirdCommandsMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		eventBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.addListener(this::onCommandRegister);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			MinecraftForge.EVENT_BUS.addListener(ClientHandler::onLogin);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		PacketHandler.init();
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		ModCommands.register(event.getDispatcher());
	}
}
