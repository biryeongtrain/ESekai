package net.biryeongtrain06.qf_stat_mod.entity.effect;


import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterStatusEffect {
    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("test", "exp"), new ExpStatusEffect());
    }
}
