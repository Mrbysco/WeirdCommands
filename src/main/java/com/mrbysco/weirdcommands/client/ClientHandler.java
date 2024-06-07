package com.mrbysco.weirdcommands.client;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import com.mrbysco.weirdcommands.network.message.EffectsToServerPayload;
import com.mrbysco.weirdcommands.network.message.LangsToServerPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@EventBusSubscriber(modid = WeirdCommandsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientHandler {
	static final Random RANDOM = new Random();
	static final List<ResourceLocation> EFFECTS = new ArrayList<>();

	@SubscribeEvent
	public static void onLogin(LoggingIn event) {
		if (Minecraft.getInstance().getConnection() != null) {
			syncValues();
		}
	}

	public static void syncValues() {
		List<String> languages = Lists.newArrayList();
		Minecraft mc = Minecraft.getInstance();
		mc.getLanguageManager().getLanguages().forEach((language, languageInfo) -> languages.add(language));
		PacketDistributor.sendToServer(new LangsToServerPayload(languages));

		Map<ResourceLocation, Resource> map = mc.getResourceManager().listResources(
				"shaders/post",
				location -> {
					String s = location.getPath();
					return s.endsWith(".json");
				}
		);
		EFFECTS.clear();
		map.forEach((location, resource) -> EFFECTS.add(location));
		EFFECTS.removeIf(location -> location.toString().equals("minecraft:shaders/post/blur.json"));
		System.out.println(EFFECTS);

		PacketDistributor.sendToServer(new EffectsToServerPayload(EFFECTS));
	}

	public static void setRandomEffect(GameRenderer gameRenderer) {
		if (gameRenderer.getMinecraft().getCameraEntity() instanceof Player) {
			if (gameRenderer.currentEffect() != null) {
				gameRenderer.shutdownEffect();
			}

			//Choose random effect
			gameRenderer.loadEffect(EFFECTS.get(RANDOM.nextInt(EFFECTS.size())) );
		}
	}
}
