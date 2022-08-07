package net.biryeongtrain06.stat_system.testComponent;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class EntityIntComponent implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(TestComponent.MAGIK, player -> new RandomIntComponent(), RespawnCopyStrategy.ALWAYS_COPY);

    }
}
