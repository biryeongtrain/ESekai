package net.biryeongtrain06.qf_stat_mod.damage;

import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

public class DamageHandler {

    private final Entity entity;

    public DamageHandler(Entity entity) {
        this.entity = entity;
    }


    public void DamageEntity(DamageSource damageSource, Elements e, float amount) {
        IDamageSource iDamageSource = (IDamageSource) entity.getDamageSources();
        QfDamageSource qfDamageSource = iDamageSource.getQfDamageSourceWithMeleeAttack(damageSource, e, amount);
        entity.damage(qfDamageSource, amount);
    }

}
