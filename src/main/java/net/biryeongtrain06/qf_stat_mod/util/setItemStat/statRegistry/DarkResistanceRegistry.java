package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.ITEM_DARK_RESISTANCE_KEY;

public class DarkResistanceRegistry extends RegistryClass{

    public DarkResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = Stats.Dark_Resistance.itemKey;
    }
}
