package net.biryeongtrain06.qf_stat_mod.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public interface MobSpawningCallback {
    Event<MobSpawningCallback> EVENT = EventFactory.createArrayBacked(MobSpawningCallback.class, (listener) -> (entity, world) -> {
        for (MobSpawningCallback handler : listener) {
            handler.onSpawn(entity, world);
        }
    });

    void onSpawn(LivingEntity entity, World world);
}
