package net.biryeongtrain06.stat_system.entity;

import net.biryeongtrain06.stat_system.util.DamageSourceAdder;
import net.biryeongtrain06.stat_system.util.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

public class onPlayerDamageToEntity {
    public static void applyDamage(Entity target, DamageSource source, float damage) {
        if (target.getWorld().isClient) {
            return;
        }
        if (target.isPlayer()) {
            return;
        }
        if (!target.isAlive()) {
            return;
        }
        DamageSourceAdder damageSource = new DamageSourceAdder(source, target, Elements.Physical, damage);
    }
}
