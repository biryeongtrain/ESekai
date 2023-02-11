package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
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
    @Inject(method = "<init>", at = @At("TAIL"))
    private void damageSourceAdder(DynamicRegistryManager dynamicRegistryManager, CallbackInfo ci) {
        this.qfDamageSource = register(QfStatSystemDamageSources.qfDamageSource);
    }

    @Override
    public DamageSource register(RegistryKey<DamageType> registryKey) {
        return this.create(registryKey);
    }

    @Override
    public DamageSource getQfDamageSource() {
        return this.qfDamageSource;
    }
}
