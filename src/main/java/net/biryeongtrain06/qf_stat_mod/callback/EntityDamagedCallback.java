package net.biryeongtrain06.qf_stat_mod.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface EntityDamagedCallback {
    Event<EntityDamagedCallback> EVENT = EventFactory.createArrayBacked(EntityDamagedCallback.class, (listener) -> (attacker, victim, source, amount) -> {
        for (EntityDamagedCallback handler : listener) {
            ActionResult result = handler.onHit(attacker, victim, source, amount);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onHit(LivingEntity attacker, LivingEntity victim, DamageSource source, float amount);
}
