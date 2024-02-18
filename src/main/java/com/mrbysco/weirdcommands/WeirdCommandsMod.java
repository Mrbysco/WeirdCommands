package com.mrbysco.weirdcommands;

import com.mojang.logging.LogUtils;
import com.mrbysco.weirdcommands.commands.ModCommands;
import com.mrbysco.weirdcommands.network.PacketHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod(WeirdCommandsMod.MOD_ID)
public class WeirdCommandsMod {
	public static final String MOD_ID = "weirdcommands";
	public static final Logger LOGGER = LogUtils.getLogger();

	public WeirdCommandsMod(IEventBus eventBus) {
		eventBus.addListener(PacketHandler::setupPackets);
		NeoForge.EVENT_BUS.addListener(this::onCommandRegister);
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		ModCommands.register(event.getDispatcher());
	}
}
