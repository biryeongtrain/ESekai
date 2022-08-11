package net.biryeongtrain06.stat_system;

import net.biryeongtrain06.stat_system.sidebar.OpenDebugBar;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.server.command.CommandManager.literal;


public class MainStatSystem implements ModInitializer {
    public static String MOD_ID ="cardinal";
    final public static Logger debugLogger = LogManager.getLogger("Qf Stat Debug");


    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("opendebugbar").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                            .executes(OpenDebugBar::OpenGUI)
            );
        });
    }
}

