package net.biryeongtrain06.qf_stat_mod.mixin;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "heal", at = @At("HEAD"))
    public void heal(float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity) {
            PlayerStat stat = PlayerDataApi.getCustomDataFor((ServerPlayerEntity) entity, PLAYER_STAT_DATA_STORAGE);
            stat.addCurrentHealth((ServerPlayerEntity) entity, amount);
        }
    }

    @Inject(method = "onSpawnPacket", at = @At("TAIL"))
    public void onLoadInit(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

    }
}
