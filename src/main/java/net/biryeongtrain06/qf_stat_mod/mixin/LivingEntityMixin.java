package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.callback.EntityDamagedCallback;
import net.biryeongtrain06.qf_stat_mod.callback.MobSpawningCallback;
import net.biryeongtrain06.qf_stat_mod.damage.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.player.PlayerStatHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.ENTITY_MODIFIERS;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "heal", at = @At("HEAD"))
    public void heal(float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            PlayerStat stat = DataStorage.loadPlayerStat(player);
            stat.addCurrentHealth(amount);
            DataStorage.savePlayerStat(player, stat);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damageHook(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (source.getType().equals(DamageTypes.PLAYER_ATTACK)) {
            return;
        }
        if (entity instanceof PlayerEntity) {
            return;
        }
        if (source.getAttacker() == null || source instanceof QfDamageSource) {
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

    @Inject(method = "areItemsDifferent", at = @At("RETURN"))
    public void getItemsChanged(ItemStack stack, ItemStack stack2, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean isChanged = cir.getReturnValue();
        if (!isChanged) return;
        if (livingEntity instanceof ServerPlayerEntity) {
            PlayerStatHandler handler = new PlayerStatHandler((ServerPlayerEntity) livingEntity);
            handler.syncItemStat(stack, stack2);
        }
        if (ENTITY_MODIFIERS.maybeGet(livingEntity).isEmpty()) {
            return;
        }
    }
}
