package net.biryeongtrain06.qf_stat_mod.Init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.biryeongtrain06.qf_stat_mod.commands.setDefense;
import net.biryeongtrain06.qf_stat_mod.commands.setLevel;
import net.biryeongtrain06.qf_stat_mod.sidebar.OpenDebugBar;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class InitCommand {
    public static void InitCommands(CommandDispatcher<ServerCommandSource> dispatcher , CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment env){
        dispatcher.register(
                literal("opendebugbar").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .executes(OpenDebugBar::OpenGUI)
        );
        dispatcher.register(
                literal("setdefense").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                        .executes(ctx -> setDefense.onExecuted(EntityArgumentType.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "value")))))

        );
        dispatcher.register(
                literal("setlevel").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                        .executes(ctx -> setLevel.onExecuted(EntityArgumentType.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "value")))))

        );
    }
}
