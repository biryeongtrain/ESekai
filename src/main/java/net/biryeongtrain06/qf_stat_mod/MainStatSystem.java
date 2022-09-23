package net.biryeongtrain06.qf_stat_mod;

import net.biryeongtrain06.qf_stat_mod.Init.InitCommand;
import net.biryeongtrain06.qf_stat_mod.commands.gameRule;
import net.biryeongtrain06.qf_stat_mod.config.ConfigHandler;
import net.biryeongtrain06.qf_stat_mod.entity.effect.ExpStatusEffect;
import net.biryeongtrain06.qf_stat_mod.entity.effect.RegisterStatusEffect;
import net.biryeongtrain06.qf_stat_mod.entity.onMobSpawn;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainStatSystem implements ModInitializer {
    public static final String MOD_ID ="qf_stat_mod";
    final public static Logger debugLogger = LogManager.getLogger("Qf Stat Debug");


    @Override
    public void onInitialize() {
        final StatusEffect EXP = new ExpStatusEffect();

        CommandRegistrationCallback.EVENT.register(InitCommand::InitCommands);

        debugLogger.info("Hello! qf_Stat_Mod is Initializing!");
        gameRule.setupGameRule();
        ServerEntityEvents.ENTITY_LOAD.register(onMobSpawn::onLoad);
        ConfigHandler.setup();
        RegisterStatusEffect.register();

    }
    }


