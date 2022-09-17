package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import net.minecraft.nbt.NbtCompound;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_EARTH_RESISTANCE_KEY;

public class EarthResistanceRegistry extends RegistryClass{
    public EarthResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = ITEM_EARTH_RESISTANCE_KEY;
    }
}
