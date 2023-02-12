package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.biryeongtrain06.qf_stat_mod.utils.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements IDamageSource {

    @Shadow
    public Registry<DamageType> registry;


    @Inject(method = "<init>", at = @At("TAIL"))
    private void damageSourceAdder(DynamicRegistryManager dynamicRegistryManager, CallbackInfo ci) {
    }

    @Override
    public QfDamageSource getQfDamageSourceWithEntityAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount) {
        return new QfDamageSource(this.registry.entryOf(QfStatSystemDamageSources.qfDamageSource), originalDamageSource, element, originalDamageAmount);
    }

    @Override
    public QfDamageSource getQfDamageSourceWithPlayerAttack(DamageSource originalDamageSource, Elements element, float originalDamageAmount) {
        return new QfDamageSource(this.registry.entryOf(QfStatSystemDamageSources.qfDamageSource), originalDamageSource, element, originalDamageAmount);
    }
}
