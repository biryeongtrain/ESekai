package net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry;

import net.biryeongtrain06.qf_stat_mod.util.Stats;


public class AttackDamageRegistry extends RegistryClass{
    public AttackDamageRegistry() {
        super.statMinValue = 1;
        super.statMaxValue = 10;
        super.key = Stats.Attack_Damage.itemKey;
    }
}
