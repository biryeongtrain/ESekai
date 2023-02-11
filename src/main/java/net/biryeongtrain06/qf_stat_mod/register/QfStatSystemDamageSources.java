package net.biryeongtrain06.qf_stat_mod.register;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class QfStatSystemDamageSources {


    public static RegistryKey<DamageType> qfDamageSource;

    public static void init() {
        qfDamageSource = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, TextHelper.getId("qf_damage"));
    }
}
