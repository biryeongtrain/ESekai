package net.biryeongtrain06.qf_stat_mod.util;

import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.UUID;

public class MobAttributeModifiers {
    public static final UUID BASE_HEALTH_FLAT_KEY = UUID.fromString("92a33bac-2df5-4a92-85a2-534087628eea");
    public static final UUID ITEM_HEALTH_FLAT_KEY = UUID.fromString("0f06435c-868b-4d99-9307-ce9ccfe38a40");
    public static final UUID HEALTH_Additive = UUID.fromString("c36571b3-6219-4cad-b508-6b35c083163f");
    public static final UUID HEALTH_Multiplicative = UUID.fromString("3e493bee-d83b-4b82-a675-341d9414a277");
    public static final EntityAttributeModifier ENTITY_HEALTH_MODIFIER_FLAT = new EntityAttributeModifier("ENTITY_HEALTH_MODIFIER_FLAT", 0, EntityAttributeModifier.Operation.fromId(1));
    public static final EntityAttributeModifier ENTITY_DAMAGE_MODIFIER_FLAT = new EntityAttributeModifier("ENTITY_DAMAGE_MODIFIER_FLAT", 0, EntityAttributeModifier.Operation.fromId(1));
}
