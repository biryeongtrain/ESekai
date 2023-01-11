package net.biryeongtrain06.qf_stat_mod.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class QfCustomDamage extends EntityDamageSource {

    final DamageSource originalDamageSource;
    final float damage;

    public QfCustomDamage(DamageSource originalDamageSource, Entity source, float amount) {
        super("custom_damage", source);
        this.setBypassesArmor();
        this.setUnblockable();
        this.setBypassesProtection();
        this.originalDamageSource = originalDamageSource;
        this.damage = amount;
    }
}
