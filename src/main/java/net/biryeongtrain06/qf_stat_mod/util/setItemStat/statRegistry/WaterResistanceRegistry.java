package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.enums.Stats;

public class WaterResistanceRegistry extends RegistryClass{

    public WaterResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = Stats.Water_Resistance.itemKey;
    }
}
