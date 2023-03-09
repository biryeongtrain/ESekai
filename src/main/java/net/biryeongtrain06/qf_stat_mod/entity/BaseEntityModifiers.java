package net.biryeongtrain06.qf_stat_mod.entity;

import net.minecraft.entity.attribute.EntityAttributeModifier;

public class BaseEntityModifiers {
    public static EntityAttributeModifier getBaseModifier(int value) {
        return new EntityAttributeModifier("base_value", value, EntityAttributeModifier.Operation.ADDITION);
    }

    public static EntityAttributeModifier getBaseModifier() {
        return new EntityAttributeModifier("base_value", 0, EntityAttributeModifier.Operation.ADDITION);
    }
}
