package net.biryeongtrain06.qf_stat_mod.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerHitByEntityCallback {
    Event<PlayerHitByEntityCallback> EVENT = EventFactory.createArrayBacked(PlayerHitByEntityCallback.class, (listener) -> (player, entity, source, amount) -> {
        for (PlayerHitByEntityCallback handler : listener) {
            handler.onHit(player, entity, source, amount);
        }
    });

    void onHit(ServerPlayerEntity player, Entity entity, DamageSource source, float amount);
}
