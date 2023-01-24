package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class DamageHandler {

    public void PlayerDamageCalculate(PlayerEntity player, DamageSource source, float amount) {
        if (!player.isInvulnerableTo(source) && source instanceof EntityDamageSource) {
            ServerPlayerEntity sPlayer = (ServerPlayerEntity) player;
            PlayerStat stat = DataStorage.loadPlayerStat(sPlayer);
            QfCustomDamage qfDamageSource = new QfCustomDamage(source, source.getSource(), Elements.PHYSICAL, amount);
            if (stat.getDodge() < (Math.random() * 100)) {
                amount *= getDamageResistance(qfDamageSource.getElement(), amount, stat);
                stat.damageHealth(qfDamageSource, player, amount);
                DataStorage.savePlayerStat(sPlayer, stat);
            }
        }
    }

    // TODO Fix this
    private float getDamageResistance(Elements element, float amount, PlayerStat stat) {

        if (element.equals(Elements.PHYSICAL)) {
            if (stat.getArmor() != 0) {
                return 1 - MathHelper.clamp(stat.getArmor() / (stat.getArmor() + (2 * amount)), -10f, 0.9f) ;
            }
        }
        else if (element.equals(Elements.FIRE)) {
            return 1 - stat.getFire_resistance();
        }
        else if (element.equals(Elements.WATER)) {
            return 1 - stat.getWater_resistance();
        }
        else if (element.equals(Elements.EARTH)) {
            return 1 - stat.getEarth_resistance();
        }
        else if (element.equals(Elements.LIGHT)) {
            return 1 - stat.getLight_resistance();
        }
        else if (element.equals(Elements.DARK)) {
            return 1 - stat.getDark_resistance();
        }

        return 1;
    }
}
