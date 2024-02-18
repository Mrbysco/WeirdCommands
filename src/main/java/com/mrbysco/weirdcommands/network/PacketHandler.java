package com.mrbysco.weirdcommands.network;

import com.mrbysco.weirdcommands.WeirdCommandsMod;
import com.mrbysco.weirdcommands.network.handler.ClientPayloadHandler;
import com.mrbysco.weirdcommands.network.handler.ServerPayloadHandler;
import com.mrbysco.weirdcommands.network.message.EffectsToServerPayload;
import com.mrbysco.weirdcommands.network.message.LangsToServerPayload;
import com.mrbysco.weirdcommands.network.message.SetEffectPayload;
import com.mrbysco.weirdcommands.network.message.SetLanguagePayload;
import com.mrbysco.weirdcommands.network.message.SetPerspectivePayload;
import com.mrbysco.weirdcommands.network.message.SetRandomEffectPayload;
import com.mrbysco.weirdcommands.network.message.SetSmoothCameraPayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class PacketHandler {
	public static void setupPackets(final RegisterPayloadHandlerEvent event) {
		final IPayloadRegistrar registrar = event.registrar(WeirdCommandsMod.MOD_ID);

		registrar.play(SetEffectPayload.ID, SetEffectPayload::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handleEffectData));
		registrar.play(SetLanguagePayload.ID, SetLanguagePayload::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handleLangData));
		registrar.play(SetPerspectivePayload.ID, SetPerspectivePayload::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handlePerspectiveData));
		registrar.play(SetRandomEffectPayload.ID, SetRandomEffectPayload::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handleRandomEffectData));
		registrar.play(SetSmoothCameraPayload.ID, SetSmoothCameraPayload::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handleSmoothCameraData));

		registrar.play(EffectsToServerPayload.ID, EffectsToServerPayload::new, handler -> handler
				.server(ServerPayloadHandler.getInstance()::handleEffectData));
		registrar.play(LangsToServerPayload.ID, LangsToServerPayload::new, handler -> handler
				.server(ServerPayloadHandler.getInstance()::handleLangData));
	}
}
