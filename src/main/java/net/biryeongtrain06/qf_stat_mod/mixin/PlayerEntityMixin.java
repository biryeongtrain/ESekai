package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.callback.EntityHitPlayerCallback;
import net.biryeongtrain06.qf_stat_mod.callback.PlayerKilledOtherCallback;
import net.biryeongtrain06.qf_stat_mod.utils.QfCustomDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("RETURN"), method = ("onKilledOther"))
    public void OnKilledOtherXP(ServerWorld world, LivingEntity entity, CallbackInfoReturnable ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        PlayerKilledOtherCallback.EVENT.invoker().onKilledOther(player, entity);
    }
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void applyDamageHook(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!source.equals(DamageSource.OUT_OF_WORLD)) {
            if (!(source instanceof QfCustomDamage)) {
                EntityHitPlayerCallback.EVENT.invoker().onHit(player, (LivingEntity) source.getSource(), source, amount);
                cir.cancel();
            }
        }
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMaxHealth()F", opcode = Opcodes.GETFIELD))
    private float disableNatureGeneration(PlayerEntity instance) {
        return 0f;
    }

    @Inject(method = "canFoodHeal", at = @At("RETURN"), cancellable = true)
    public void disableNatureGeneration(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
