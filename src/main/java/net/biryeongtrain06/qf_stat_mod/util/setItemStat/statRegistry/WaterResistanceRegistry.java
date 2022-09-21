package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.ITEM_WATER_RESISTANCE_KEY;

public class WaterResistanceRegistry extends RegistryClass{

    public WaterResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = Stats.Water_Resistance.itemKey;
    }
}
