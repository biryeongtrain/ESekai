package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.minecraft.nbt.NbtCompound;


import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.getKeyRegistry;

public class StatRegistry {
    protected static float RARITY_MODIFIER = 1.05F;
    protected static float LEVEL_MODIFIER = 1.01F;
    public static StatRegistry decideStat(String key) {
        StatRegistry statRegistry = getKeyRegistry(key);
        return statRegistry;
    }

    public NbtCompound setStat(int level, int rarity) {
        return null;
    }
}
