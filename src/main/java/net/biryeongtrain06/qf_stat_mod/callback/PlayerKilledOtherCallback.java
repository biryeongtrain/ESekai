package net.biryeongtrain06.qf_stat_mod.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerKilledOtherCallback {
    Event<PlayerKilledOtherCallback> EVENT = EventFactory.createArrayBacked(PlayerKilledOtherCallback.class, (listeners) -> (killer, victims) -> {
        for (PlayerKilledOtherCallback handler : listeners) {
            handler.onKilledOther(killer, victims);
        }
    });

    void onKilledOther(PlayerEntity killer, LivingEntity victim);
}
