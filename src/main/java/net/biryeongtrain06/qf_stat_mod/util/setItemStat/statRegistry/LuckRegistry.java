package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

public class LuckRegistry extends RegistryClass{
    public LuckRegistry() {
        super.statMinValue = 1;
        super.statMaxValue = 5;
        super.key = Stats.Luck.itemKey;
    }
}
