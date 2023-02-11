package net.biryeongtrain06.qf_stat_mod.interfaces;

import net.minecraft.class_8110;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.RegistryKey;

public interface IDamageSource {
    DamageSource register(RegistryKey<class_8110> registryKey);
}
