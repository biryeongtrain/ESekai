package net.biryeongtrain06.stat_system.util;

import net.biryeongtrain06.stat_system.entity.onPlayerDamageToEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class applyDamageHook {
        public static void hook (Entity target, Entity source, DamageSource dmgSource, float damage, Elements element) {
            if (target instanceof PlayerEntity) {

            }
            else if (target instanceof Entity && dmgSource.getAttacker().isPlayer()) {
                onPlayerDamageToEntity.applyDamage(target, (PlayerEntity) source, dmgSource, damage, element);
            }
        }

}
