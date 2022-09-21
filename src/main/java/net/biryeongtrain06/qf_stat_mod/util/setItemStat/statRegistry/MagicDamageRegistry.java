package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;


public class MagicDamageRegistry extends RegistryClass{
    public MagicDamageRegistry() {
        super.statMinValue = 1;
        super.statMaxValue = 10;
        super.key = Stats.Magic_Damage.itemKey;
    }
}
