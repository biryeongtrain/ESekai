package net.biryeongtrain06.qf_stat_mod.register;

import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.registry.DynamicRegistryManager;

public class QfTestDamageSource extends DamageSources {
    IDamageSource iDamageSource = (IDamageSource) this;
    private final DamageSource qfDamageSource;
    public QfTestDamageSource(DynamicRegistryManager dynamicRegistryManager) {
        super(dynamicRegistryManager);
        this.qfDamageSource = iDamageSource.register(QfStatSystemDamageSources.qfDamageSource);
    }

    public DamageSource getQfDamageSource() {
        return this.qfDamageSource;
    }
}
