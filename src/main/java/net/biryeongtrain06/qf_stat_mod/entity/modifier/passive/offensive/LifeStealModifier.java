package net.biryeongtrain06.qf_stat_mod.entity.modifier.passive.offensive;

import net.biryeongtrain06.qf_stat_mod.entity.modifier.Modifier;
import net.biryeongtrain06.qf_stat_mod.entity.modifier.ModifierSubType;
import net.biryeongtrain06.qf_stat_mod.entity.modifier.ModifierType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

import static net.biryeongtrain06.qf_stat_mod.entity.modifier.ModifierSubType.OFFENSIVE;
import static net.biryeongtrain06.qf_stat_mod.entity.modifier.ModifierType.PASSIVE;

public class LifeStealModifier implements Modifier {

    @Override
    public String getName() {
        return "life_steal";
    }

    @Override
    public ModifierType getModifierType() {
        return PASSIVE;
    }

    @Override
    public ModifierSubType getModifierSubType() {
        return OFFENSIVE;
    }

    @Override
    public void onDamageToTarget(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {
        float multiplier = attacker.getEntityWorld().getDifficulty().getId() / 2;
        attacker.heal(amount * multiplier);
    }
}
