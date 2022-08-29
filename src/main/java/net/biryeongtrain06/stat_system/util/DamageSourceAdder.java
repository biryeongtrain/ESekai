package net.biryeongtrain06.stat_system.util;

import net.biryeongtrain06.stat_system.MainStatSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class DamageSourceAdder extends EntityDamageSource {

    Elements elements;
    public float readlDamage;
    DamageSource source;

    public DamageSourceAdder(DamageSource s, Entity source, Elements elements, float dmg) {
        super(source(s), source);
        this.elements = elements;
        this.bypassesArmor();
        this.readlDamage = dmg;
        this.source = s;
    }

    static String source(DamageSource s) {
        if (s == null) {
            return new String(MainStatSystem.MOD_ID + ".customdamage");
        }
        return s.name;
    }
}
