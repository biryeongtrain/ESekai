package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.ITEM_EARTH_RESISTANCE_KEY;

public class EarthResistanceRegistry extends RegistryClass{
    public EarthResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = Stats.Earth_Resistance.itemKey;
    }
}
