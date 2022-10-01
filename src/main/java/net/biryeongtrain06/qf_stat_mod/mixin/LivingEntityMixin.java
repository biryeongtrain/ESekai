package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.component.StatComponent;
import net.biryeongtrain06.qf_stat_mod.entity.onMobSpawn;
import net.biryeongtrain06.qf_stat_mod.util.DamageSourceAdder;
import net.biryeongtrain06.qf_stat_mod.util.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.util.applyDamageHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.ITEM_ELEMENT_KEY;

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
    @Inject(method = "modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "HEAD"), cancellable = true)
    public void enchantedEntityHook(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (source instanceof DamageSourceAdder) {
            ci.setReturnValue(amount);
        }
    }

    @Inject(method = "modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "RETURN"), cancellable = true)
    public void enchantedEntityHookReturn(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        //TODO : Curios 관련 패치

        if(source instanceof DamageSourceAdder) {
            ci.setReturnValue(amount);
        }
    }

    @Inject(method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "HEAD"), cancellable = true)
    public void armor2Damage(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (source instanceof DamageSourceAdder) {
            ci.setReturnValue(amount);
        }
    }

    //TODO 엔티티 아직 후킹 안함
    @Inject(method = "applyDamage", at = @At(value = "HEAD"), cancellable = true)
    public void setDamageSource(DamageSource source, float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (source instanceof DamageSourceAdder) {
            if (source.getAttacker() instanceof PlayerEntity && entity instanceof HostileEntity) {
                PlayerEntity player = (PlayerEntity) source.getAttacker();
                ItemStack item = player.getMainHandStack();
                if (!item.getNbt().getString(ITEM_ELEMENT_KEY).equals("")) {

                    Elements elements = Elements.valueOf(item.getNbt().getString(ITEM_ELEMENT_KEY));
                    applyDamageHook.hook(entity, player, source, amount, elements);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "onSpawnPacket", at = @At("TAIL"))
    public void onSpawnPacket(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
            onMobSpawn.onLoad(entity);
    }
}
