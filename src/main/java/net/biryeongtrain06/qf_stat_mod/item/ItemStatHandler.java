package net.biryeongtrain06.qf_stat_mod.item;

import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.Nbt2EnumMapAdapter;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.UUID;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

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

    public boolean addItemStat(StatTypes e, StatSubTag tag, float value) {
        if (ITEM_STACK.isEmpty()) return false;
        NbtCompound statCompound = ITEM_STACK.getOrCreateSubNbt(STAT_KEY);
        NbtCompound nbtCompound;
        if (statCompound.contains(e.getName())) {
            nbtCompound = statCompound.getCompound(e.getName());
            if (nbtCompound.contains(tag.name())) {
                value += nbtCompound.getFloat(tag.name());
            }
        } else {
            nbtCompound = new NbtCompound();
        }
        nbtCompound.putFloat(tag.toString(), value);

        statCompound.put(e.getName(), nbtCompound);
        ITEM_STACK.getNbt().put(STAT_KEY, statCompound);
        return true;
    }
    public boolean setItemStat(StatTypes e, StatSubTag tag, float value) {
        if (ITEM_STACK.isEmpty()) return false;
        NbtCompound statCompound = ITEM_STACK.getOrCreateSubNbt(STAT_KEY);
        NbtCompound nbtCompound;
        if (statCompound.contains(e.getName())) {
            nbtCompound = statCompound.getCompound(e.getName());
        } else {
            nbtCompound = new NbtCompound();
        }
        nbtCompound.putFloat(tag.toString(), value);
        if (e.isOnlyPercent() && tag != StatSubTag.PERCENT) return false;
        statCompound.put(e.getName(), nbtCompound);
        ITEM_STACK.getNbt().put(STAT_KEY, statCompound);
        return true;
    }

    public float getItemStat(StatTypes e, StatSubTag tag) {
        if (ITEM_STACK.isEmpty()) return 0;
        NbtCompound statCompound = ITEM_STACK.getOrCreateSubNbt(STAT_KEY);

        if (!statCompound.contains(e.getName())) return 0;
        if (statCompound.getCompound(e.getName()).contains(tag.name())) {
            return statCompound.getCompound(e.getName()).getFloat(tag.name());
        }
        return 0;
    }

    /**
     * 이 메소드는 특정 스탯에 따른 수치를 반환합니다.
     * @param e {@link StatTypes} 대상 스탯
     * @return {@link StatTypes#isOnlyPercent()} 가 <code>true</code> 일 경우 0~1.0, <code>false</code>일 경우는 저장된 수치가 나옵니다.
     */
    public float getTotalItemStatValue(StatTypes e) {
        if (ITEM_STACK.isEmpty()) return 0;
        NbtCompound statCompound = ITEM_STACK.getSubNbt(STAT_KEY);
        if (statCompound == null) return 0;
        EnumMap<StatTypes, IStats> map = Nbt2EnumMapAdapter.ConvertNbtCompoundAsMap(statCompound);
        if (map.get(e) == null) return 0;

        return map.get(e).getTotalValue();
    }

    public boolean setItemStat(StatTypes e, StatSubTag tag, Identifier id, float value) {
        if (e.isOnlyPercent() && tag != StatSubTag.PERCENT) {
            debugLogger.error("An error occurred while setting item stat. : PERCENT type's tag must be PERCENT.");
            return false;
        }
        EnumMap<StatTypes, IStats> map;
        if (ITEM_STACK.getSubNbt(STAT_KEY) != null) {
            NbtCompound statCompound = ITEM_STACK.getSubNbt(STAT_KEY);
            map = Nbt2EnumMapAdapter.ConvertNbtCompoundAsMap(statCompound);
        } else {
            map = new EnumMap<>(StatTypes.class);
        }

        if (!map.containsKey(e)) {
            map.put(e, e.isOnlyPercent() ? new PercentStat(e) : new FloatStat(e));
        }
        if (!map.get(e).tryReplaceInstance(id, value, tag)) map.get(e).addStatInstance(id, value, tag);
        ITEM_STACK.getNbt().put(STAT_KEY, Nbt2EnumMapAdapter.ConvertMapAsNbtCompound(map));
        return true;
    }

    public boolean removeItemStat(StatTypes e, StatSubTag tag, Identifier id ) {
        if (e.isOnlyPercent() && tag != StatSubTag.PERCENT) {
            debugLogger.error("An error occurred while removing item stat. : PERCENT type's tag must be PERCENT");
            return false;
        }
        if (ITEM_STACK.getSubNbt(STAT_KEY) == null) {
            return false;
        }
        var map = Nbt2EnumMapAdapter.ConvertNbtCompoundAsMap(ITEM_STACK.getSubNbt(STAT_KEY));
        boolean bool1 = map.containsKey(e) ? map.get(e).removeStatInstance(id, tag) : false;
        NbtCompound statCompound = Nbt2EnumMapAdapter.ConvertMapAsNbtCompound(map);
        return bool1;
    }
}
