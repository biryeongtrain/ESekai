package net.biryeongtrain06.qf_stat_mod.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerJoinCallback {
    Event<PlayerJoinCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinCallback.class,
            (listeners) -> (player) -> {
                for (PlayerJoinCallback callback : listeners) {
                    callback.join(player);
                }
            });

    void join(ServerPlayerEntity player);
}
