package com.mrbysco.weirdcommands.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mrbysco.weirdcommands.WeirdCommandsMod;
import com.mrbysco.weirdcommands.network.PacketHandler;
import com.mrbysco.weirdcommands.network.message.SetEffectMessage;
import com.mrbysco.weirdcommands.network.message.SetLanguageMessage;
import com.mrbysco.weirdcommands.network.message.SetPerspectiveMessage;
import com.mrbysco.weirdcommands.network.message.SetRandomEffectMessage;
import com.mrbysco.weirdcommands.network.message.SetSmoothCameraMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.command.EnumArgument;

import java.util.Collection;
import java.util.List;

public class ModCommands {
	public static List<String> languages = List.of("en_us");
	public static List<ResourceLocation> effects = Lists.newArrayList();

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(WeirdCommandsMod.MOD_ID);
		root.requires((sourceStack) -> sourceStack.hasPermission(2))
				.then(Commands.literal("lang")
						.then(Commands.argument("players", EntityArgument.players())
								.then(Commands.argument("id", StringArgumentType.word()).suggests((cs, builder) ->
										SharedSuggestionProvider.suggest(languages, builder)).executes(ModCommands::setLanguage))))
				.then(Commands.literal("effect")
						.then(Commands.argument("players", EntityArgument.players())
								.then(Commands.argument("id", ResourceLocationArgument.id()).suggests((cs, builder) -> {
									List<String> values = Lists.newArrayList();
									effects.forEach((effect) -> values.add(effect.toString()));
									return SharedSuggestionProvider.suggest(values, builder);
								}).executes(ModCommands::setEffect))
								.then(Commands.literal("clear").executes(ModCommands::clearEffect))))
				.then(Commands.literal("randomEffect")
						.then(Commands.argument("players", EntityArgument.players()).executes(ModCommands::setRandomEffect)))
				.then(Commands.literal("perspective")
						.then(Commands.argument("players", EntityArgument.players())
								.then(Commands.argument("perspective", EnumArgument.enumArgument(Perspective.class)).executes(ModCommands::setPerspective))))
				.then(Commands.literal("smoothCamera")
						.then(Commands.argument("players", EntityArgument.players())
								.then(Commands.argument("enabled", BoolArgumentType.bool()).executes(ModCommands::setSmoothCamera))));
		dispatcher.register(root);
	}

	private static int setLanguage(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		String langID = StringArgumentType.getString(context, "id");
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
		for (ServerPlayer player : players) {
			PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SetLanguageMessage(langID));
		}

		MutableComponent component = Component.literal(langID).withStyle(ChatFormatting.GOLD);
		if (players.size() == 1) {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.language.set.success.single",
					component, players.iterator().next().getName()).withStyle(ChatFormatting.YELLOW), true);
		} else {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.language.set.success.multiple",
					component, players.size()).withStyle(ChatFormatting.YELLOW), true);
		}

		return 0;
	}

	private static int setEffect(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		ResourceLocation effectID = ResourceLocationArgument.getId(context, "id");
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
		for (ServerPlayer player : players) {
			PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SetEffectMessage(effectID));
		}

		MutableComponent component = Component.literal(effectID.toString()).withStyle(ChatFormatting.GOLD);
		if (players.size() == 1) {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.effect.set.success.single",
					component, players.iterator().next().getName()).withStyle(ChatFormatting.YELLOW), true);
		} else {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.effect.set.success.multiple",
					component, players.size()).withStyle(ChatFormatting.YELLOW), true);
		}

		return 0;
	}

	private static int clearEffect(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
		for (ServerPlayer player : players) {
			PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SetEffectMessage(null));
		}

		if (players.size() == 1) {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.effect.clear.success.single",
					players.iterator().next().getName()).withStyle(ChatFormatting.YELLOW), true);
		} else {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.effect.clear.success.multiple",
					players.size()).withStyle(ChatFormatting.YELLOW), true);
		}

		return 0;
	}

	private static int setRandomEffect(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
		for (ServerPlayer player : players) {
			PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SetRandomEffectMessage());
		}

		if (players.size() == 1) {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.effect.random.success.single",
					players.iterator().next().getName()).withStyle(ChatFormatting.YELLOW), true);
		} else {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.effect.random.success.multiple",
					players.size()).withStyle(ChatFormatting.YELLOW), true);
		}

		return 0;
	}

	private static int setPerspective(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		final Perspective perspective = context.getArgument("perspective", Perspective.class);
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
		for (ServerPlayer player : players) {
			PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SetPerspectiveMessage(perspective));
		}

		MutableComponent component = Component.literal(perspective.getPerspectiveName()).withStyle(ChatFormatting.GOLD);
		if (players.size() == 1) {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.perspective.set.success.single",
					component, players.iterator().next().getName()).withStyle(ChatFormatting.YELLOW), true);
		} else {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.perspective.set.success.multiple",
					component, players.size()).withStyle(ChatFormatting.YELLOW), true);
		}

		return 0;
	}

	private static int setSmoothCamera(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		final boolean enabled = BoolArgumentType.getBool(context, "enabled");
		Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
		for (ServerPlayer player : players) {
			PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SetSmoothCameraMessage(enabled));
		}

		MutableComponent component = Component.literal(String.valueOf(enabled)).withStyle(ChatFormatting.GOLD);
		if (players.size() == 1) {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.smooth_camera.set.success.single",
					component, players.iterator().next().getName()).withStyle(ChatFormatting.YELLOW), true);
		} else {
			context.getSource().sendSuccess(() -> Component.translatable("weirdcommands.commands.smooth_camera.set.success.multiple",
					component, players.size()).withStyle(ChatFormatting.YELLOW), true);
		}

		return 0;
	}
}