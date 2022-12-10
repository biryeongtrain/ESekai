package net.biryeongtrain06.qf_stat_mod.event;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.player.IServerPlayerEntity;
import net.biryeongtrain06.qf_stat_mod.player.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.sidebar.PlayerStatBar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.core.jmx.Server;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.DATA_STORAGE;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;


public class CallbackInit {
    public static void playerJoinCallback(ServerPlayerEntity player) {
        IServerPlayerEntity iPlayer = (IServerPlayerEntity) player;
        if (!iPlayer.isPlayedBefore()) {
            var PlayerStat = new PlayerStat();
            PlayerDataApi.setCustomDataFor(player, DATA_STORAGE, PlayerStat);
            PlayerStatBar.Open(player);
        }
    }

    public static void playerKilledCallback(PlayerEntity killer, LivingEntity victim) {
        ServerPlayerEntity killPlayer = (ServerPlayerEntity) killer;
        PlayerStat stat = PlayerDataApi.getCustomDataFor(killPlayer, DATA_STORAGE);
        stat.addXP(1);
        PlayerDataApi.setCustomDataFor(killPlayer, DATA_STORAGE, stat);
        debugLogger.debug("Killed!");
    }
}
