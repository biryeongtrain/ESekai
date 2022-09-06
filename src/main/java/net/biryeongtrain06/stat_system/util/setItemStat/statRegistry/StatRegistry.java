package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import net.minecraft.nbt.NbtCompound;


import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.getKeyRegistry;

public class StatRegistry {
    protected static float RARITY_MODIFIER = 1.5F;
    protected static float LEVEL_MODIFIER = 1.1F;
    public NbtCompound decideStat(String key, NbtCompound nbt, int level, int rarity) {
        StatRegistry statRegistry = getKeyRegistry(key);
        return statRegistry.setStat(nbt, level, rarity);
    }

    public NbtCompound setStat(NbtCompound nbt, int level, int rarity) {
        return null;
    }
}
