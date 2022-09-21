package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.ITEM_LIGHT_RESISTANCE_KEY;

public class LightResistanceRegistry extends RegistryClass{
    public LightResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = Stats.Light_Resistance.itemKey;
    }
}
