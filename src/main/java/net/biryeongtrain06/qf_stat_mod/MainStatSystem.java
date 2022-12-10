package net.biryeongtrain06.qf_stat_mod;


import eu.pb4.playerdata.api.storage.JsonDataStorage;
import eu.pb4.playerdata.api.storage.PlayerDataStorage;
import net.biryeongtrain06.qf_stat_mod.command.InitCommand;
import net.biryeongtrain06.qf_stat_mod.event.PlayerJoinCallback;
import net.biryeongtrain06.qf_stat_mod.event.PlayerKilledOtherCallback;
import net.biryeongtrain06.qf_stat_mod.event.CallbackInit;
import net.biryeongtrain06.qf_stat_mod.player.PlayerStat;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainStatSystem implements ModInitializer {

    public static final String MOD_ID ="qf_stat_mod";
    final public static Logger debugLogger = LogManager.getLogger("Qf Stat Debug");
    public static final PlayerDataStorage<PlayerStat> DATA_STORAGE = new JsonDataStorage<>("player_gson", PlayerStat.class);


    @Override
    public void onInitialize() {

        CommandRegistrationCallback.EVENT.register(InitCommand::InitCommand);
        PlayerJoinCallback.EVENT.register(CallbackInit::playerJoinCallback);
        PlayerKilledOtherCallback.EVENT.register(CallbackInit::playerKilledCallback);
    }
    }


