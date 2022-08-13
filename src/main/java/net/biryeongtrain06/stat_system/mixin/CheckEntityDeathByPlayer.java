package net.biryeongtrain06.stat_system.mixin;

import net.biryeongtrain06.stat_system.component.PlayerStatComponent;
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

@Mixin(LivingEntity.class)
public class CheckEntityDeathByPlayer {
    @Inject(method = "onDeath", at = @At("HEAD"))
    //if Mob Dead by player, give player xp in statComponent
    public void StatSystem$onKilledXPTracker(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (!livingEntity.world.isClient || !livingEntity.isRemoved()) {
            Entity entity = damageSource.getAttacker();
            if(entity instanceof ServerPlayerEntity player) {
                if (entity instanceof WitherEntity || entity instanceof EnderDragonEntity) {
                    PlayerStatComponent.STAT.get(player).addXp(30);
                } else if (entity instanceof WardenEntity) {
                    PlayerStatComponent.STAT.get(player).addXp(10);
                } else {
                    PlayerStatComponent.STAT.get(player).addXp(1);
                }
            }
        }
    }
}
