package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.event.EntityHitPlayerCallback;
import net.biryeongtrain06.qf_stat_mod.event.PlayerKilledOtherCallback;
import net.biryeongtrain06.qf_stat_mod.utils.QfCustomDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("RETURN"), method = ("onKilledOther"))
    public void OnKilledOtherXP(ServerWorld world, LivingEntity entity, CallbackInfoReturnable ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        PlayerKilledOtherCallback.EVENT.invoker().onKilledOther(player, entity);
    }
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    public void applyDamageHook(DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!source.equals(DamageSource.OUT_OF_WORLD)) {
            if (!(source instanceof QfCustomDamage)) {
                ci.cancel();
                EntityHitPlayerCallback.EVENT.invoker().onHit(player, (LivingEntity) source.getSource(), source, amount);
            }
        }
    }
}
