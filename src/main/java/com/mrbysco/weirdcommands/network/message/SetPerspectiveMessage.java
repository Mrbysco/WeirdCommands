package com.mrbysco.weirdcommands.network.message;

import com.mrbysco.weirdcommands.commands.Perspective;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

public class SetPerspectiveMessage {
	private final Perspective perspective;

	public SetPerspectiveMessage(Perspective perspective) {
		this.perspective = perspective;
	}

	public static SetPerspectiveMessage decode(final FriendlyByteBuf buffer) {
		return new SetPerspectiveMessage(Perspective.getByName(buffer.readUtf()));
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUtf(perspective.getPerspectiveName());
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				UpdateEvent.updatePerspective(this.perspective).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class UpdateEvent {
		private static SafeRunnable updatePerspective(Perspective perspective) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					Minecraft minecraft = Minecraft.getInstance();
					switch (perspective) {
						default -> minecraft.options.setCameraType(CameraType.FIRST_PERSON);
						case THIRD_PERSON_BACK -> minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
						case THIRD_PERSON_FRONT -> minecraft.options.setCameraType(CameraType.THIRD_PERSON_FRONT);
					}
				}
			};
		}
	}
}
