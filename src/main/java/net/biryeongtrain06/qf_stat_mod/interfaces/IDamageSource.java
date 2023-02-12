package net.biryeongtrain06.qf_stat_mod.interfaces;


import net.biryeongtrain06.qf_stat_mod.utils.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

public interface IDamageSource {

    QfDamageSource getQfDamageSourceWithEntityAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount);
    QfDamageSource getQfDamageSourceWithPlayerAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount);
}
