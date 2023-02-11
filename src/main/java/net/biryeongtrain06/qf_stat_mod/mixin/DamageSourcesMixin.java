package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements IDamageSource {

    private DamageSource qfDamageSource;

    @Shadow
    public abstract DamageSource create(RegistryKey<DamageType> registryKey);
    @Shadow
    public abstract DamageSource create(RegistryKey<DamageType> key, @Nullable Entity attacker);
    @Shadow
    public abstract DamageSource create(RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void damageSourceAdder(DynamicRegistryManager dynamicRegistryManager, CallbackInfo ci) {
        this.qfDamageSource = register(QfStatSystemDamageSources.qfDamageSource);
    }

    @Override
    public DamageSource register(RegistryKey<DamageType> registryKey) {
        return this.create(registryKey);
    }

    @Override
    public DamageSource register(RegistryKey<DamageType> registryKey, @Nullable Entity attacker) {
        return this.create(registryKey, attacker);
    }

    @Override
    public DamageSource register(RegistryKey<DamageType> registryKey, @Nullable Entity source, @Nullable Entity attacker) {
        return this.create(registryKey, source, attacker);
    }

    @Override
    public DamageSource getQfDamageSource() {
        return this.qfDamageSource;
    }

    @Override
    public DamageSource getQfDamageSourceWithEntityAttack(LivingEntity attacker) {
        return this.register(QfStatSystemDamageSources.qfDamageSource, attacker);
    }

    @Override
    public DamageSource getQfDamageSourceWithPlayerAttack(PlayerEntity attacker) {
        return this.register(QfStatSystemDamageSources.qfDamageSource, attacker);
    }
}
