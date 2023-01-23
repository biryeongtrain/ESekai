package net.biryeongtrain06.qf_stat_mod.api;

import eu.pb4.playerdata.api.PlayerDataApi;
import eu.pb4.playerdata.api.storage.JsonDataStorage;
import eu.pb4.playerdata.api.storage.PlayerDataStorage;
import net.minecraft.server.network.ServerPlayerEntity;

public class DataStorage {
    public static final PlayerDataStorage<PlayerStat> PLAYER_STAT_DATA_STORAGE = new JsonDataStorage<>("player_stat", PlayerStat.class);
    public static final PlayerDataStorage<TestData> TEST_PLAYER_DATA_STORAGE = new JsonDataStorage<>("test", TestData.class);
    public static PlayerStat loadPlayerStat(ServerPlayerEntity player) {
        return PlayerDataApi.getCustomDataFor(player, PLAYER_STAT_DATA_STORAGE);
    }
    public static void savePlayerStat(ServerPlayerEntity player, PlayerStat playerStat) {
        PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, playerStat);
    }
}
