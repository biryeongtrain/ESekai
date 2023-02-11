package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registerable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTypes.class)
public interface DamageTypesMixin {

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void registerDamageType(Registerable<DamageType> damageTypeRegisterable, CallbackInfo ci) {
        damageTypeRegisterable.register(QfStatSystemDamageSources.qfDamageSource, new DamageType("qfCustomDamage", 0.1F, DamageEffects.HURT));
    }
}
