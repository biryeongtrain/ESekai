package net.biryeongtrain06.stat_system;

import net.biryeongtrain06.stat_system.Init.InitCommand;
import net.biryeongtrain06.stat_system.commands.gameRule;
import net.biryeongtrain06.stat_system.config.ConfigHandler;
import net.biryeongtrain06.stat_system.entity.onMobSpawn;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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


