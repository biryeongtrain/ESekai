package net.biryeongtrain06.qf_stat_mod.mixin;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.event.EntityHitPlayerCallback;
import net.biryeongtrain06.qf_stat_mod.player.PlayerStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.DATA_STORAGE;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "heal", at = @At("HEAD"))
    public void heal(float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity) {
            PlayerStat stat = PlayerDataApi.getCustomDataFor((ServerPlayerEntity) entity, DATA_STORAGE);
            stat.addCurrentHealth(amount);
            entity.setHealth(stat.getCurrentHealth() / stat.getMaxHealth() * 20);
        }
    }
}
