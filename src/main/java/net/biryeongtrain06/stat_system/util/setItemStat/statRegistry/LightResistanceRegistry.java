package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_LIGHT_RESISTANCE_KEY;

public class LightResistanceRegistry extends RegistryClass{
    public LightResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = ITEM_LIGHT_RESISTANCE_KEY;
    }
}
