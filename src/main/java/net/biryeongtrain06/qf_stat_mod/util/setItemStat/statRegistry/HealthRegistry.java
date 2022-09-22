package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.enums.Stats;

public class HealthRegistry extends RegistryClass{
    public HealthRegistry() {
        super.statMinValue = 5;
        super.statMaxValue = 15;
        super.key = Stats.Health.itemKey;
    }
}
