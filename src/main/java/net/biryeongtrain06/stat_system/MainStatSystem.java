package net.biryeongtrain06.stat_system;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.biryeongtrain06.stat_system.commands.gameRule;
import net.biryeongtrain06.stat_system.commands.setDefense;
import net.biryeongtrain06.stat_system.commands.setLevel;
import net.biryeongtrain06.stat_system.sidebar.OpenDebugBar;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.server.command.CommandManager.literal;


public class MainStatSystem implements ModInitializer {
    public static final String MOD_ID ="cardinal";
    final public static Logger debugLogger = LogManager.getLogger("Qf Stat Debug");


    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
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
        });
        gameRule.setupGameRule();
        }
    }


