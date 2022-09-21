package net.biryeongtrain06.qf_stat_mod.util;

import net.biryeongtrain06.qf_stat_mod.MainStatSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class DamageSourceAdder extends EntityDamageSource {

    Elements elements;
    public float realDamage;
    DamageSource source;

    public DamageSourceAdder(DamageSource s, Entity source, Elements elements, float dmg) {
        super(source(s), source);
        this.elements = elements;
        this.bypassesArmor();
        this.realDamage = dmg;
        this.source = s;
    }

    static String source(DamageSource s) {
        if (s == null) {
            return MainStatSystem.MOD_ID + ".customdamage";
        }
        return s.name;
    }
}
