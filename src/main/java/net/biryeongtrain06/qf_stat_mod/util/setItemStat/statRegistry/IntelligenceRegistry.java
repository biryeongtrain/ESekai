package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.enums.Stats;

public class IntelligenceRegistry extends RegistryClass{
    public IntelligenceRegistry() {
        super.statMinValue = 1;
        super.statMaxValue = 5;
        super.key = Stats.Intelligence.itemKey;
    }
}
