package com.mrbysco.weirdcommands.client;

import com.mrbysco.weirdcommands.network.PacketHandler;
import com.mrbysco.weirdcommands.network.message.EffectsToServerMessage;
import com.mrbysco.weirdcommands.network.message.LangsToServerMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ClientHandler {

	public static void onLogin(LoggingIn event) {
		if (Minecraft.getInstance().getConnection() != null) {
			syncValues();
		}
	}

	public static void syncValues() {
		List<String> languages = Lists.newArrayList();
		Minecraft mc = Minecraft.getInstance();
		mc.getLanguageManager().getLanguages().forEach((language, languageInfo) -> languages.add(language));
		PacketHandler.CHANNEL.sendToServer(new LangsToServerMessage(languages));

		List<ResourceLocation> effects = List.of(GameRenderer.EFFECTS);
		PacketHandler.CHANNEL.sendToServer(new EffectsToServerMessage(effects));
	}
}
