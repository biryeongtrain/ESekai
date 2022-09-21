package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;

public class ManaRegistry extends RegistryClass{
    public ManaRegistry() {
        super.statMinValue = 5;
        super.statMaxValue = 25;
        super.key = Stats.Magic_Damage.itemKey;
    }
}
