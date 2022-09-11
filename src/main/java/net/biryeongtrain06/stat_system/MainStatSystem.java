package net.biryeongtrain06.stat_system;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.biryeongtrain06.stat_system.Init.InitCommand;
import net.biryeongtrain06.stat_system.commands.gameRule;
import net.biryeongtrain06.stat_system.commands.setDefense;
import net.biryeongtrain06.stat_system.commands.setLevel;
import net.biryeongtrain06.stat_system.config.ConfigHandler;
import net.biryeongtrain06.stat_system.entity.onMobSpawn;
import net.biryeongtrain06.stat_system.sidebar.OpenDebugBar;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.server.command.CommandManager.literal;


public class MainStatSystem implements ModInitializer {
    public static final String MOD_ID ="qf_stat_mod";
    final public static Logger debugLogger = LogManager.getLogger("Qf Stat Debug");


    @Override
    public void onInitialize() {

        //register Commands
        CommandRegistrationCallback.EVENT.register(InitCommand::InitCommands);

        debugLogger.info("Hello! qf_Stat_Mod is Initializing!");
        gameRule.setupGameRule();
        ServerEntityEvents.ENTITY_LOAD.register(onMobSpawn::onLoad);
        ConfigHandler.setup();
        }
    }


