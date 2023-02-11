package net.biryeongtrain06.qf_stat_mod.interfaces;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

public interface IDamageSource {

    DamageSource register(RegistryKey<DamageType> registryKey);
    DamageSource register(RegistryKey<DamageType> registryKey, @Nullable Entity attacker);
    DamageSource register(RegistryKey<DamageType> registryKey, @Nullable Entity source, @Nullable Entity attacker);

    DamageSource getQfDamageSource();
    DamageSource getQfDamageSourceWithEntityAttack(LivingEntity attacker);
    DamageSource getQfDamageSourceWithPlayerAttack(PlayerEntity attacker);
}
