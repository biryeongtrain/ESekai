package net.biryeongtrain06.qf_stat_mod.interfaces;


import net.biryeongtrain06.qf_stat_mod.damage.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.damage.DamageSource;

public interface IDamageSource {

    QfDamageSource getQfDamageSourceWithProjectileAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount);
    QfDamageSource getQfDamageSourceWithMeleeAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount);
    QfDamageSource getQfDamageSourceWithEnvironment(DamageSource originalDamageSource, float originalDamageAmount);
}
