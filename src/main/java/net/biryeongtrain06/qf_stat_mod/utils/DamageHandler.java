package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class DamageHandler {

    public static void PlayerDamageCalculate(PlayerEntity player, DamageSource source, float amount) {
        if (!player.isInvulnerableTo(source)) {
            ServerPlayerEntity sPlayer = (ServerPlayerEntity) player;
            PlayerStat stat = DataStorage.loadPlayerStat(sPlayer);
            stat.damageHealth(source, player, amount);
            DataStorage.savePlayerStat(sPlayer, stat);
        }
    }
}
