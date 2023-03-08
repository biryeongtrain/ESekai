package net.biryeongtrain06.qf_stat_mod.api;

import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.*;

public class IStatGsonAdapter implements JsonSerializer<EnumMap<StatTypes, IStats>>, JsonDeserializer<EnumMap<StatTypes, IStats>> {


    @Override
    public JsonElement serialize(EnumMap<StatTypes, IStats> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        src.keySet().forEach(key -> {
            JsonObject stat = new JsonObject();
            var iStat = src.get(key);
            if (iStat instanceof FloatStat) {
                stat.add(FLAT.name(), convertMapAsJsonObject(iStat.getCloneInstances(FLAT)));
                stat.add(PERCENT.name(), convertMapAsJsonObject(iStat.getCloneInstances(PERCENT)));
                stat.add(MULTIPLIER.name(), convertMapAsJsonObject(iStat.getCloneInstances(MULTIPLIER)));
            } else {
                stat.add(PERCENT.name(), convertMapAsJsonObject(iStat.getCloneInstances(PERCENT)));
            }
            jsonObject.add(key.getName(), stat);
        });
        return jsonObject;
    }

    private JsonObject convertMapAsJsonObject(Object2FloatOpenHashMap<Identifier> map) {
        JsonObject jsonObject = new JsonObject();
        map.keySet().forEach(key ->
            jsonObject.add(key.toString(), new JsonPrimitive(map.getFloat(key))));

        return jsonObject;
    }

    @Override
    public EnumMap<StatTypes, IStats> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);
        if (!(json instanceof JsonObject)) throw new JsonParseException("stat must be JsonObject");
        JsonObject stat = json.getAsJsonObject();

        for (String key : stat.keySet()) {
            var statType = stat.get(key).getAsJsonObject();
            StatTypes statEnum = StatTypes.getStatByName(key);

            map.put(statEnum, convertJsonObjectAsMap(statType));
            }

        return map;
    }

    private IStats convertJsonObjectAsMap(JsonObject object) {
        IStats iStats = createStatClass(object);

        for (String tag : object.keySet()) {
            StatSubTag statSubTag = StatSubTag.getStatByName(tag);
            JsonObject statInstance = object.get(tag).getAsJsonObject();

            for (String id : statInstance.keySet()) {
                iStats.addStatInstance(new Identifier(id), statInstance.get(id).getAsFloat(), statSubTag);
            }
        }
        return iStats;
    }

    private IStats createStatClass(JsonObject object) {
        if (object.size() == 3) {
            return new FloatStat();
        }
        return new PercentStat();
    }
}
