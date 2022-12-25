package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.MainStatSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class DamageSourceAdder extends EntityDamageSource {

    public float realDamage;
    DamageSource source;
    public DamageSourceAdder(DamageSource s, Entity source, float dmg) {
        super(source(s), source);
        this.realDamage = dmg;
        this.source = s;
        this.bypassesArmor();
    }

    static String source(DamageSource s) {
        if (s == null) {
            return MainStatSystem.MOD_ID + ".customDamage";
        }
        return s.name;
    }
}
