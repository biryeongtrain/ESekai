package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.biryeongtrain06.stat_system.MainStatSystem;
import net.biryeongtrain06.stat_system.stat.EntityStat;
import net.biryeongtrain06.stat_system.stat.StatSystem;
import net.minecraft.util.Identifier;

public class PlayerStatComponent implements EntityComponentInitializer {

    public static final ComponentKey<StatComponent> STAT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MainStatSystem.MOD_ID, "stat"), StatComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(STAT, StatSystem::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
