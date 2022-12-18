package net.biryeongtrain06.qf_stat_mod.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public interface EntityHitPlayerCallback {
    Event<EntityHitPlayerCallback> EVENT = EventFactory.createArrayBacked(EntityHitPlayerCallback.class, (listener) -> (player, entity, source, amount) -> {
        for (EntityHitPlayerCallback handler : listener) {
            handler.onHit(player, entity, source, amount);
        }
    });

    void onHit(PlayerEntity player, LivingEntity entity, DamageSource source, float amount);
}
