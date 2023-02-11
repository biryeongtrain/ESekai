package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.biryeongtrain06.qf_stat_mod.register.QfTestDamageSource;
import net.minecraft.class_8107;
import net.minecraft.class_8110;
import net.minecraft.class_8111;
import net.minecraft.registry.Registerable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_8111.class)
public class DamageTypesMixin {

    @Inject(method = "method_48839", at = @At("TAIL"))
    private static void registerDamageType(Registerable<class_8110> registerable, CallbackInfo ci) {
        registerable.register(QfStatSystemDamageSources.qfDamageSource, new class_8110("qfCustomDamage", 0.1F, class_8107.HURT));
    }
}
