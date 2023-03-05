package net.biryeongtrain06.qf_stat_mod.api;

import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.EnumMap;

public class IStatGsonAdapter implements JsonSerializer<EnumMap<StatTypes, IStats>> {
    @Override
    public IStats deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

    }

    @Override
    public JsonElement serialize(EnumMap<StatTypes, IStats> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        src.keySet().forEach(key -> {
            JsonObject stat = new JsonObject();
            var iStat = src.get(key);
            if (iStat instanceof FloatStat) {
                stat.add(StatSubTag.FLAT.name(), iStat.getInstances(StatSubTag.FLAT))
            }
            jsonObject.add(key.getName(), )
        });
    }

    private JsonObject convertMapAsJsonObject(Object2FloatOpenHashMap<Identifier> map) {
        JsonObject jsonObject = new JsonObject();
        map.keySet().forEach();
    }
}
