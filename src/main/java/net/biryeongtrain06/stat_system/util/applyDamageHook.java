package net.biryeongtrain06.stat_system.util;

import net.biryeongtrain06.stat_system.entity.onPlayerDamageToEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class applyDamageHook {
        public static void hook (Entity target, DamageSource source, float damage) {
            if (target instanceof PlayerEntity) {

            }
            else if (target instanceof Entity && source.getAttacker().isPlayer()) {
                onPlayerDamageToEntity.applyDamage(target, source, damage);
            }
        }

}
