package net.biryeongtrain06.qf_stat_mod.utils;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;

public class DamageHandler {

    public static void PlayerDamageCalculate(PlayerEntity player, DamageSource source, float amount) {
        if (!player.isInvulnerableTo(source)) {
            ServerPlayerEntity sPlayer = (ServerPlayerEntity) player;
            PlayerStat stat = DataStorage.loadPlayerStat(sPlayer);
            //stat.addCurrentHealth((ServerPlayerEntity) player, -amount);
            stat.damageHealth(source, player, amount);
            DataStorage.savePlayerStat(sPlayer, stat);
        }
    }
}
