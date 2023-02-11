package net.biryeongtrain06.qf_stat_mod.register;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.class_8110;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class QfStatSystemDamageSources {


    public static RegistryKey<class_8110> qfDamageSource = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, TextHelper.getId("qf_damage"));


}
