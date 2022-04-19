package com.mrbysco.weirdcommands.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

public class SetLanguageMessage {
	private final String language;

	public SetLanguageMessage(String language) {
		this.language = language;
	}

	public static SetLanguageMessage decode(final FriendlyByteBuf buffer) {
		return new SetLanguageMessage(buffer.readUtf());
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUtf(language);
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				UpdateEvent.update(this.language).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class UpdateEvent {
		private static SafeRunnable update(String language) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					Minecraft minecraft = Minecraft.getInstance();
					LanguageManager languageManager = minecraft.getLanguageManager();
					LanguageInfo languageInfo = languageManager.getLanguage(language);
					languageManager.setSelected(languageInfo);
					minecraft.reloadResourcePacks();
				}
			};
		}
	}
}
