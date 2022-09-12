package net.biryeongtrain06.stat_system.mixin;

import net.biryeongtrain06.stat_system.util.applyDamageHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ELEMENT_KEY;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "attack", at = @At(value = "HEAD"), cancellable = true)
    public void playerAttackHook(Entity target, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if(!player.getMainHandStack().getNbt().getString(ELEMENT_KEY).equals("")) {
            //TODO 데미지 공식 넣기
            applyDamageHook.hook(target, player, );
        }
    }

}
