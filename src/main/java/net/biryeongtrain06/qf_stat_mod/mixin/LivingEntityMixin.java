package net.biryeongtrain06.qf_stat_mod.mixin;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.callback.EntityDamagedCallback;
import net.biryeongtrain06.qf_stat_mod.callback.MobSpawningCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "heal", at = @At("HEAD"))
    public void heal(float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            PlayerStat stat = DataStorage.loadPlayerStat(player);
            stat.addCurrentHealth(player, amount);
            DataStorage.savePlayerStat(player, stat);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damageHook(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(source.getType().equals(DamageTypes.PLAYER_ATTACK))) {
            return;
        }
        if (entity instanceof PlayerEntity) {
            return;
        }
        if (source.getAttacker().isPlayer()) {
            EntityDamagedCallback.EVENT.invoker().onHit(source.getAttacker(), entity, source, amount);
            cir.cancel();
        }
    }
    @Inject(method = "onSpawnPacket", at = @At("TAIL"))
    public void onLoadInit(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        MobSpawningCallback.EVENT.invoker().onSpawn(entity, entity.getWorld());
    }
}
