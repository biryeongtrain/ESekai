package net.biryeongtrain06.qf_stat_mod.utils.builder;

import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.EnumMap;

public class StatDeserializer implements JsonDeserializer<Object2ObjectOpenHashMap<Identifier, EnumMap<StatTypes, IStats>>> {
    @Override
    public Object2ObjectOpenHashMap<Identifier, EnumMap<StatTypes, IStats>> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!(jsonElement instanceof JsonObject)) throw new JsonParseException("Stats must be JsonObject.");
        Object2ObjectOpenHashMap<Identifier, EnumMap<StatTypes, IStats>> map = new Object2ObjectOpenHashMap<>();
        JsonObject object = jsonElement.getAsJsonObject();

        return map;
    }
}
