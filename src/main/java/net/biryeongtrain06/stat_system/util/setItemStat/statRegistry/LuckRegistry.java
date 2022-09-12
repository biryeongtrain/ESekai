package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_LUCK_KEY;

public class LuckRegistry extends StatRegistry{
    @Override
    public NbtCompound setStat(int level, int rarity) {
        float maxValue = 5 * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        float minValue = 1 * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        NbtCompound result = new NbtCompound();
        result.putString("name", ITEM_LUCK_KEY);
        result.putInt("value", MathHelper.ceil(((Math.random() * maxValue)) + minValue));
        return result;
    }
}
