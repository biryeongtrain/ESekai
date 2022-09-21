package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

public class DefenseRegistry extends RegistryClass{
    public DefenseRegistry() {
        super.statMinValue = 5;
        super.statMaxValue = 20;
        super.key = Stats.Defense.itemKey;
    }
}
