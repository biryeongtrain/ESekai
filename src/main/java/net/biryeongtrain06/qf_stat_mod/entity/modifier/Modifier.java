package net.biryeongtrain06.qf_stat_mod.entity.modifier;

import net.biryeongtrain06.qf_stat_mod.entity.modifier.stat.ModifierTier;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;

import java.util.EnumMap;

public interface Modifier {
    String getName();
    ModifierType getModifierType();
    ModifierSubType getModifierSubType();
    ModifierTier getModifierTier();

    default EnumMap<StatTypes, IStats> getStatsMap() {
        return null;
    }
    default long getCoolDown() {
        return 0;
    }

    default boolean canUseAbility(MobEntity mobEntity) {
        return false;
    }

    default void onAbilityUsed(MobEntity mobEntity) {

    }

    default void passiveApply(MobEntity mobEntity) {

    }
    default void onDamaged(LivingEntity entity, DamageSource source, float amount) {}

    default boolean dealsSelfDamage() {
       return false;
    }

    default void onAttack(LivingEntity attacker, LivingEntity entity) {}

    default void onDamageToTarget(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {}

    default void onDeath(LivingEntity entity, DamageSource source) {}

    default void onRangedAttack(LivingEntity owner, PersistentProjectileEntity projectile) {}
}
