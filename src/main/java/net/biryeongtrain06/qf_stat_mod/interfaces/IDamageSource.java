package net.biryeongtrain06.qf_stat_mod.interfaces;


import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;

public interface IDamageSource {
    DamageSource register(RegistryKey<DamageType> registryKey);
}
