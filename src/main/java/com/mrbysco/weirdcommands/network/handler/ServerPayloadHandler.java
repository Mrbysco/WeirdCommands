package com.mrbysco.weirdcommands.network.handler;

import com.mrbysco.weirdcommands.commands.ModCommands;
import com.mrbysco.weirdcommands.network.message.EffectsToServerPayload;
import com.mrbysco.weirdcommands.network.message.LangsToServerPayload;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ServerPayloadHandler {
	public static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

	public static ServerPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleEffectData(final EffectsToServerPayload payload, final PlayPayloadContext context) {
		// Do something with the data, on the main thread
		context.workHandler().submitAsync(() -> {
					ModCommands.effects.clear();
					if (!payload.values().isEmpty()) {
						ModCommands.effects.addAll(payload.values());
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.packetHandler().disconnect(Component.translatable("weirdcommands.networking.effects_to_server.failed", e.getMessage()));
					return null;
				});
	}

	public void handleLangData(final LangsToServerPayload payload, final PlayPayloadContext context) {
		// Do something with the data, on the main thread
		context.workHandler().submitAsync(() -> {
					if (payload.values().isEmpty()) {
						ModCommands.languages.add("en_us");
					} else {
						ModCommands.languages.clear();
						ModCommands.languages.addAll(payload.values());
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.packetHandler().disconnect(Component.translatable("weirdcommands.networking.effects_to_server.failed", e.getMessage()));
					return null;
				});
	}
}
