package com.mrbysco.weirdcommands.network.handler;

import com.mrbysco.weirdcommands.client.ClientHandler;
import com.mrbysco.weirdcommands.network.message.SetEffectPayload;
import com.mrbysco.weirdcommands.network.message.SetLanguagePayload;
import com.mrbysco.weirdcommands.network.message.SetPerspectivePayload;
import com.mrbysco.weirdcommands.network.message.SetRandomEffectPayload;
import com.mrbysco.weirdcommands.network.message.SetSmoothCameraPayload;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
	private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

	public static ClientPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleEffectData(final SetEffectPayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Minecraft minecraft = Minecraft.getInstance();
					if (payload.effect().isEmpty()) {
						minecraft.gameRenderer.clearPostEffect();
					} else {
						minecraft.gameRenderer.setPostEffect(payload.effect().get());
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("weirdcommands.networking.set_effect.failed", e.getMessage()));
					return null;
				});
	}

	public void handleLangData(final SetLanguagePayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Minecraft minecraft = Minecraft.getInstance();
					LanguageManager languageManager = minecraft.getLanguageManager();
					LanguageInfo languageInfo = languageManager.getLanguage(payload.language());
					if (languageInfo != null) {
						languageManager.setSelected(payload.language());
					}
					minecraft.reloadResourcePacks();
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("weirdcommands.networking.set_language.failed", e.getMessage()));
					return null;
				});
	}

	public void handlePerspectiveData(final SetPerspectivePayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Minecraft minecraft = Minecraft.getInstance();
					switch (payload.perspective()) {
						default -> minecraft.options.setCameraType(CameraType.FIRST_PERSON);
						case THIRD_PERSON_BACK -> minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
						case THIRD_PERSON_FRONT -> minecraft.options.setCameraType(CameraType.THIRD_PERSON_FRONT);
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("weirdcommands.networking.set_perspective.failed", e.getMessage()));
					return null;
				});
	}

	public void handleRandomEffectData(final SetRandomEffectPayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Minecraft minecraft = Minecraft.getInstance();
					ClientHandler.setRandomEffect(minecraft.gameRenderer);
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("weirdcommands.networking.set_random_effect.failed", e.getMessage()));
					return null;
				});
	}

	public void handleSmoothCameraData(final SetSmoothCameraPayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Minecraft minecraft = Minecraft.getInstance();
					minecraft.options.smoothCamera = payload.enabled();
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("weirdcommands.networking.set_smooth_camera.failed", e.getMessage()));
					return null;
				});
	}
}
