package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.ITEM_FIRE_RESISTANCE_KEY;

public class FireResistanceRegistry extends RegistryClass{
    public FireResistanceRegistry() {
        super.statMinValue = 2;
        super.statMaxValue = 5;
        super.key = Stats.Fire_Resistance.itemKey;
    }
}
