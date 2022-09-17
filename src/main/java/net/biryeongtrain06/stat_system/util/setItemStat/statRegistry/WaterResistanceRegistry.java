package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_WATER_RESISTANCE_KEY;

public class WaterResistanceRegistry extends RegistryClass{

    public WaterResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = ITEM_WATER_RESISTANCE_KEY;
    }
}
