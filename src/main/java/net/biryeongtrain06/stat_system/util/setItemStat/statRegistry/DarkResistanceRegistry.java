package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_DARK_RESISTANCE_KEY;
import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_WATER_RESISTANCE_KEY;

public class DarkResistanceRegistry extends RegistryClass{

    public DarkResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = ITEM_DARK_RESISTANCE_KEY;
    }
}
