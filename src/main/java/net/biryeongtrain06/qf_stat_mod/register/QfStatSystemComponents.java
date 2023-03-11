package net.biryeongtrain06.qf_stat_mod.register;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.biryeongtrain06.qf_stat_mod.components.CommonEntityValue;
import net.biryeongtrain06.qf_stat_mod.components.EntityComponent;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.ENTITY_MODIFIERS;

public class QfStatSystemComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(HostileEntity.class, ENTITY_MODIFIERS, EntityComponent::new);
    }
}
