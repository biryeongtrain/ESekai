package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.biryeongtrain06.qf_stat_mod.damage.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements IDamageSource {

    @Shadow
    public Registry<DamageType> registry;


    @Override
    public QfDamageSource getQfDamageSourceWithEntityAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount) {
        return new QfDamageSource(this.registry.entryOf(QfStatSystemDamageSources.qfDamageSource), originalDamageSource, element, originalDamageAmount);
    }

    @Override
    public QfDamageSource getQfDamageSourceWithPlayerAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount) {
        return new QfDamageSource(this.registry.entryOf(QfStatSystemDamageSources.qfDamageSource), originalDamageSource, element, originalDamageAmount);
    }
}
