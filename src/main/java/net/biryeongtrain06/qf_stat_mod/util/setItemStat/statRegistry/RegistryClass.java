package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;


public class RegistryClass extends StatRegistry{
    int statMinValue = 0;
    int statMaxValue = 0;
    String key = "key";

    @Override
    public NbtCompound setStat(int level, int rarity) {

        float maxValue = statMaxValue * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        float minValue = statMinValue * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        int value = MathHelper.ceil(Math.random() * maxValue + minValue);
        NbtCompound result = new NbtCompound();
        result.putString("name", key);
        result.putInt("value", value);
        return result;
    }
}
