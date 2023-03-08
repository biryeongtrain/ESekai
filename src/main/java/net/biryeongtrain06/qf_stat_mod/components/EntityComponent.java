package net.biryeongtrain06.qf_stat_mod.components;

import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.ENTITY_INIT_STATS;

public class EntityComponent {
    private final EnumMap<StatTypes, IStats> instance;
    private int level;

    public EntityComponent(Entity provider) {
        this.instance = setEntityStat(EntityType.getId(provider.getType()));
    }

    private EnumMap<StatTypes, IStats> setEntityStat(Identifier id) {
        EnumMap<StatTypes, IStats> map = ENTITY_INIT_STATS.get(TextHelper.getId("default_mob"));
        EnumMap<StatTypes, IStats> replaceMap = ENTITY_INIT_STATS.get(id);

        if (replaceMap.isEmpty() || replaceMap == null) return map;
        replaceMap.keySet().forEach(key -> map.put(key, replaceMap.get(key)));
        return map;
    }

    public IStats getStat(StatTypes stat) {
        return instance.get(stat);
    }

    private int setHealth() {
        return 1;
    }


}
