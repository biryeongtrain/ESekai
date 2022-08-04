package njoyshadow.cardinal.component;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

import static njoyshadow.cardinal.component.TestComponent.MAGIK;

public class EntityIntComponent implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MAGIK, player -> new RandomIntComponent(), RespawnCopyStrategy.ALWAYS_COPY);

    }
}
