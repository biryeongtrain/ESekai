package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_MANA_KEY;

public class ManaRegistry extends StatRegistry{
    @Override
    public NbtCompound setStat(NbtCompound nbt, int level, int rarity) {
        float maxValue = 25 * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        float minValue = 5 * ((rarity + 1) * RARITY_MODIFIER) * (level * LEVEL_MODIFIER);
        NbtCompound result = nbt;
        result.putInt(ITEM_MANA_KEY, MathHelper.ceil(((Math.random() * maxValue)) + minValue));
        return result;
    }
}
