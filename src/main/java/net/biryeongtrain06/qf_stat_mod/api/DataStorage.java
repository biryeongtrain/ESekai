package net.biryeongtrain06.qf_stat_mod.api;

import eu.pb4.playerdata.api.PlayerDataApi;
import eu.pb4.playerdata.api.storage.JsonDataStorage;
import eu.pb4.playerdata.api.storage.PlayerDataStorage;
import net.minecraft.server.network.ServerPlayerEntity;

public class DataStorage {
    private static final PlayerDataStorage<PlayerStat> PLAYER_STAT_DATA_STORAGE = new JsonDataStorage<>("player_stat", PlayerStat.class);

    /**
     * 플레이어의 스텟을 불러옵니다.
     * @param player {@link ServerPlayerEntity} 대상의 플레이어입니다.
     * @return {@link PlayerStat} 플레이어의 스텟을 반환합니다. 만약 플레이어가 한번도 접속하지 않은 경우 null을 반환합니다.
     */
    public static PlayerStat loadPlayerStat(ServerPlayerEntity player) {
        return PlayerDataApi.getCustomDataFor(player, PLAYER_STAT_DATA_STORAGE);
    }

    /**
     * 플레이어의 스텟을 저장합니다.
     * @param player {@link ServerPlayerEntity} 대상 플레이어입니다.
     * @param playerStat {@link PlayerStat} 저장 할 플레이어 스텟입니다.
     */
    public static void savePlayerStat(ServerPlayerEntity player, PlayerStat playerStat) {
        PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, playerStat);
    }

    private static <T extends PlayerDataStorage<?>> void register(T DataStorage) {
        PlayerDataApi.register(DataStorage);
    }

    /**
     * 저장소를 PlayerDataAPI에 등록합니다. 데이터가 불러오기 전에 꼭 등록해야합니다.
     */
    public static void registerAllDataStorage() {
        register(PLAYER_STAT_DATA_STORAGE);
    }
}
