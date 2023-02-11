package net.biryeongtrain06.qf_stat_mod.register;

import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.vanilla.VanillaDamageTypeTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

import static net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources.qfDamageSource;

public class QfTagBuilder extends VanillaDamageTypeTagProvider {
    public QfTagBuilder(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> maxChainedNeighborUpdates) {
        super(output, maxChainedNeighborUpdates);
    }

    public void configure(RegistryWrapper.WrapperLookup lookup) {
        super.configure(lookup);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(qfDamageSource);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(qfDamageSource);
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE).add(qfDamageSource);
    }
}
