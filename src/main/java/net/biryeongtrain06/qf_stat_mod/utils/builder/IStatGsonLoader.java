package net.biryeongtrain06.qf_stat_mod.utils.builder;

import com.google.gson.JsonObject;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;

import java.io.File;
import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public interface IStatGsonLoader {
    int REQUIRED_TO_FLAT_STAT = StatSubTag.values().length;
    String getDir();
    void setStats();
    default EnumMap<StatTypes, IStats> makeMap(JsonObject stats, File file) {

        EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);

        for (String key : stats.keySet()) {
            JsonObject statObj = stats.get(key).getAsJsonObject();
            StatTypes statType = StatTypes.getStatByName(key.toLowerCase());

            if (statObj.size() == REQUIRED_TO_FLAT_STAT) {
                float flatValue = statObj.get(StatSubTag.FLAT.name()).getAsFloat();
                float percentValue = statObj.get(StatSubTag.PERCENT.name()).getAsFloat();
                float multiValue = statObj.get(StatSubTag.MULTIPLIER.name()).getAsFloat();

                map.put(statType, new FloatStat(flatValue, percentValue, multiValue));
            } else if (statObj.has(StatSubTag.PERCENT.name())) {
                float percentValue = statObj.get(StatSubTag.PERCENT.name()).getAsFloat();

                map.put(statType, new PercentStat(percentValue));
            } else {
                debugLogger.error("Stat {} is not valid. Check this json. Path : {}", key, file.toString());
            }
        }
        return map;
    }
}
