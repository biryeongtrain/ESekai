package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_DEFENSE_KEY;

public class DefenseRegistry extends StatRegistry{
    @Override
    public NbtCompound setStat(int level, int rarity) {
        float maxValue = 20 * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        float minValue = 5 * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        NbtCompound result = new NbtCompound();
        result.putString("name", ITEM_DEFENSE_KEY);
        result.putInt("value", MathHelper.ceil(((Math.random() * maxValue)) + minValue));
        return result;
    }
}
