package net.biryeongtrain06.stat_system.mixin;

import net.biryeongtrain06.stat_system.component.StatComponent;
import net.biryeongtrain06.stat_system.util.DamageSourceAdder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    //if Mob Dead by player, give player xp in statComponent
    public void StatSystem$onKilledXPTracker(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (!livingEntity.world.isClient || !livingEntity.isRemoved()) {
            Entity entity = damageSource.getAttacker();
            if(entity instanceof ServerPlayerEntity player) {
                if (entity instanceof WitherEntity || entity instanceof EnderDragonEntity) {
                    StatComponent.PLAYERSTAT.get(player).addXp(30);
                } else if (entity instanceof WardenEntity) {
                    StatComponent.PLAYERSTAT.get(player).addXp(10);
                } else {
                    StatComponent.PLAYERSTAT.get(player).addXp(1);
                }
            }
        }
    }
    @Inject(method = "Lnet/minecraft/entity/LivingEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "HEAD"), cancellable = true)
    public void enchantedEntityHook(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (source instanceof DamageSourceAdder) {
            ci.setReturnValue(amount);
        }
    }

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "RETURN"), cancellable = true)
    public void enchantedEntityHookReturn(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        //TODO : Curios 관련 패치

        if(source instanceof DamageSourceAdder) {
            ci.setReturnValue(amount);
        }
    }

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "HEAD"), cancellable = true)
    public void armor2Damage(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (source instanceof DamageSourceAdder) {
            ci.setReturnValue(amount);
        }
    }
}
