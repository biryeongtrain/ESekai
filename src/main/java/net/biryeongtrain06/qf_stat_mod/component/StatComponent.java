package net.biryeongtrain06.qf_stat_mod.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.biryeongtrain06.qf_stat_mod.MainStatSystem;
import net.biryeongtrain06.qf_stat_mod.stat.EntityStatSystem;
import net.biryeongtrain06.qf_stat_mod.stat.PlayerStatSystem;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class StatComponent implements EntityComponentInitializer {

    public static final ComponentKey<PlayerBaseStatComponentInterface> PLAYERSTAT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MainStatSystem.MOD_ID, "playerstat"), PlayerBaseStatComponentInterface.class);
    public static final ComponentKey<EntityStatComponentInterface> ENTITY_STAT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MainStatSystem.MOD_ID, "entitystat"), EntityStatComponentInterface.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYERSTAT, PlayerStatSystem::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(MobEntity.class, ENTITY_STAT, EntityStatSystem::new);
    }
}
