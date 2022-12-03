package net.biryeongtrain06.qf_stat_mod.player;

import eu.pb4.playerdata.api.storage.JsonDataStorage;
import eu.pb4.playerdata.api.storage.PlayerDataStorage;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerHook {
    public static final PlayerDataStorage<PlayerStat> DATA_STORAGE = new JsonDataStorage<>("player_gson", PlayerStat.class);

    public static void RegisterPlayerStat(ServerPlayerEntity player) {

    }
}
