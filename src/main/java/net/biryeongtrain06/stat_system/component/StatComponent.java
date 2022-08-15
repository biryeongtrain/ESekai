package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.biryeongtrain06.stat_system.MainStatSystem;
import net.biryeongtrain06.stat_system.stat.EntityStatSystem;
import net.biryeongtrain06.stat_system.stat.PlayerStatSystem;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;

public class StatComponent implements EntityComponentInitializer {

    public static final ComponentKey<PlayerStatComponentInterface> PLAYERSTAT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MainStatSystem.MOD_ID, "playerstat"), PlayerStatComponentInterface.class);
    public static final ComponentKey<EntityStatComponentInterface> ENTITY_STAT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MainStatSystem.MOD_ID, "entitystat"), EntityStatComponentInterface.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYERSTAT, PlayerStatSystem::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(HostileEntity.class, ENTITY_STAT, e -> new EntityStatSystem(e));
    }
}
