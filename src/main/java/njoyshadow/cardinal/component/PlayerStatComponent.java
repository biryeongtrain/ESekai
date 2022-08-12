package njoyshadow.cardinal.component;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;
import njoyshadow.cardinal.Cardinal;
import njoyshadow.cardinal.stat.StatSystem;

public class PlayerStatComponent implements EntityComponentInitializer {

    public static final ComponentKey<StatComponent> STAT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Cardinal.MOD_ID, "stat"), StatComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(STAT, StatSystem::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
