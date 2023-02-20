package net.biryeongtrain06.qf_stat_mod.api;

import lombok.Getter;
import net.biryeongtrain06.qf_stat_mod.stats.HealthFlat;
import net.biryeongtrain06.qf_stat_mod.stats.HealthPercent;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums;

import java.util.EnumMap;

public class NewPlayerStat {
    @Getter
    int maxHealth = 100;
        @Getter
        int currentHealth;
        private EnumMap<StatEnums, IStats> map = new EnumMap<>(StatEnums.class);

    public NewPlayerStat() {
            init();
        }

        public void init() {
            map.put(StatEnums.HEALTH_FLAT, new HealthFlat());
            map.put(StatEnums.HEALTH_INCREASE_PERCENT, new HealthPercent());

            map.get(StatEnums.HEALTH_FLAT).addStatInstance(TextHelper.getId("base_value"), 10);
    }
}
