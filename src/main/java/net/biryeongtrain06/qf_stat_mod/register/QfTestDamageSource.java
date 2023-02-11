package net.biryeongtrain06.qf_stat_mod.register;

import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.minecraft.class_8109;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.DynamicRegistryManager;

public class QfTestDamageSource extends class_8109 {
    IDamageSource iDamageSource = (IDamageSource) this;
    private final DamageSource qfDamageSource;
    public QfTestDamageSource(DynamicRegistryManager dynamicRegistryManager) {
        super(dynamicRegistryManager);
        this.qfDamageSource = iDamageSource.register(QfStatSystemDamageSources.qfDamageSource);
    }

}
