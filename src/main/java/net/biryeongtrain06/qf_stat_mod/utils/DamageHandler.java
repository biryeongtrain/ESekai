package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class DamageHandler {

    public static void PlayerDamageCalculate(PlayerEntity player, DamageSource source, float amount) {
        if (!player.isInvulnerableTo(source)) {
            ServerPlayerEntity sPlayer = (ServerPlayerEntity) player;
            PlayerStat stat = DataStorage.loadPlayerStat(sPlayer);
            EntityDamageSource qfDamageSource = new QfCustomDamage(source, source.getSource(), Elements.PHYSICAL, amount);
            if (stat.getDodge() < (Math.random() * 100)) {
                amount = stat.getArmor() / stat.getArmor() + 10 * amount;
                stat.damageHealth(source, player, amount);
                DataStorage.savePlayerStat(sPlayer, stat);
            }
        }
    }

    // TODO Fix this
    public float calculateDamageSource(Elements element, float amount, PlayerStat stat) {
        if (element.equals(Elements.PHYSICAL)) {
            float damageReduceRate = stat.getArmor() / stat.getArmor() + 5 * stat.getArmor();
            return amount = amount / damageReduceRate;
        }
        if (element.equals(Elements.FIRE)) {
            float damageReduceRate = 1 - stat.getFire_resistance();
            return amount = amount * damageReduceRate;
        }
        if (element.equals(Elements.WATER)) {
            float damageReduceRate = 1 - stat.getWater_resistance();
            return amount = amount * damageReduceRate;
        }
        return amount;
    }
}
