package net.biryeongtrain06.qf_stat_mod.damage;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class DamageHandler {

    private final Entity entity;

    public DamageHandler(Entity entity) {
        this.entity = entity;
    }

    /**
     * Use only Entity is Player.
     * @param source Original DamageSource
     * @param amount Damage amount
     */
    public void PlayerDamageCalculate(DamageSource source, float amount) {
        if (!(entity instanceof ServerPlayerEntity)) { return; }
        ServerPlayerEntity player = (ServerPlayerEntity) entity;
        if (!player.isInvulnerableTo(source) && source instanceof DamageSource) {
            IDamageSource qfDamageSources = (IDamageSource) player.getDamageSources();
            PlayerStat stat = DataStorage.loadPlayerStat(player);
            QfDamageSource qfDamageSource = qfDamageSources.getQfDamageSourceWithEntityAttack(source, Elements.PHYSICAL, amount);
            if (stat.getDodge() < (Math.random() * 100)) {
               amount *= getDamageResistance(qfDamageSource.getElement(), amount, stat);
               stat.damageHealth(qfDamageSource, player, amount);
               DataStorage.savePlayerStat(player, stat);
            }
        }
    }

    public void DamageEntity(DamageSource damageSource, Elements e, float amount) {
        IDamageSource iDamageSource = (IDamageSource) entity.getDamageSources();
        QfDamageSource qfDamageSource = iDamageSource.getQfDamageSourceWithPlayerAttack(damageSource, e, amount);
        entity.damage(qfDamageSource, amount);
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
