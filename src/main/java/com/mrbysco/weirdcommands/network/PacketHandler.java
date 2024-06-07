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
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketHandler {
	public static void setupPackets(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(WeirdCommandsMod.MOD_ID);

		registrar.playToClient(SetEffectPayload.ID, SetEffectPayload.CODEC, ClientPayloadHandler.getInstance()::handleEffectData);
		registrar.playToClient(SetLanguagePayload.ID, SetLanguagePayload.CODEC, ClientPayloadHandler.getInstance()::handleLangData);
		registrar.playToClient(SetPerspectivePayload.ID, SetPerspectivePayload.CODEC, ClientPayloadHandler.getInstance()::handlePerspectiveData);
		registrar.playToClient(SetRandomEffectPayload.ID, SetRandomEffectPayload.CODEC, ClientPayloadHandler.getInstance()::handleRandomEffectData);
		registrar.playToClient(SetSmoothCameraPayload.ID, SetSmoothCameraPayload.CODEC, ClientPayloadHandler.getInstance()::handleSmoothCameraData);

		registrar.playToServer(EffectsToServerPayload.ID, EffectsToServerPayload.CODEC, ServerPayloadHandler.getInstance()::handleEffectData);
		registrar.playToServer(LangsToServerPayload.ID, LangsToServerPayload.CODEC, ServerPayloadHandler.getInstance()::handleLangData);
	}
}
