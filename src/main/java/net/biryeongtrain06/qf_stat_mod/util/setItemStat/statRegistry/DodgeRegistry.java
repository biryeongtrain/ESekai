package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

public class DodgeRegistry extends RegistryClass{
    public DodgeRegistry() {
        super.statMinValue = 5;
        super.statMaxValue = 10;
        super.key = Stats.Dodge.itemKey;
    }
}
