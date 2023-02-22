package net.biryeongtrain06.qf_stat_mod.item;

import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class ItemStatHandler {
    private final ItemStack ITEM_STACK;
    private final UUID DAMAGE_KEY = UUID.fromString("407855f1-f109-4147-87cd-154ee8308eef");
    public static final String STAT_KEY = "stats";

    public ItemStatHandler(ItemStack ITEM_STACK) {
        this.ITEM_STACK = ITEM_STACK;
    }

    public double getItemDamage() {
        EntityAttributeModifier modifier = (EntityAttributeModifier) ITEM_STACK.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        return modifier.getValue();
    }
    public boolean setItemDamage(float amount) {
        if (ITEM_STACK.isEmpty()) return false;
       ITEM_STACK.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, getDamageModifier(amount), EquipmentSlot.MAINHAND);
       return true;
    }

    private EntityAttributeModifier getDamageModifier(float amount) {
        return new EntityAttributeModifier(DAMAGE_KEY, MOD_ID + "_damage", amount, EntityAttributeModifier.Operation.ADDITION);
    }

    public boolean setItemStat(StatTypes e, float value) {
        if (ITEM_STACK.isEmpty()) return false;
        NbtCompound statCompound = ITEM_STACK.getOrCreateSubNbt(STAT_KEY);
        statCompound.putFloat(e.getName(), value);
        ITEM_STACK.getNbt().put(STAT_KEY, statCompound);
        return true;
    }

    public float getItemStat(StatTypes e) {
        if (ITEM_STACK.isEmpty()) return 0;
        NbtCompound statCompound = ITEM_STACK.getOrCreateSubNbt(STAT_KEY);

        if (!statCompound.contains(e.getName())) return 0;

        return statCompound.getFloat(e.getName());
    }
}
