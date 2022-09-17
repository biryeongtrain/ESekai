package net.biryeongtrain06.stat_system.util.setItemStat.statRegistry;

import static net.biryeongtrain06.stat_system.stat.PlayerStat.FIRE_RESISTANCE_KEY;
import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_FIRE_RESISTANCE_KEY;

public class FireResistanceRegistry extends RegistryClass{
    public FireResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = ITEM_FIRE_RESISTANCE_KEY;
    }
}
