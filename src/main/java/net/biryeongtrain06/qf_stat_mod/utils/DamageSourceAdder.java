package net.biryeongtrain06.qf_stat_mod.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

public class DamageSourceAdder extends EntityDamageSource {

    public DamageSourceAdder(Entity source) {
        super("custom_damage", source);
    }
}
