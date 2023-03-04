package net.biryeongtrain06.qf_stat_mod.utils.builder;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.*;

public class TestBaseStatGsonLoader {
    public static Object2ObjectOpenHashMap<Identifier, EnumMap<StatTypes, IStats>> ENTITY_INIT_STATS = new Object2ObjectOpenHashMap<>();
    private final int REQUIRED_TO_FLAT_STAT = StatSubTag.values().length;
    public final String baseStatDir = MOD_DIR + "/resources/data/" + MOD_ID + "/test_data/base";

    private List<Path> files;

    public TestBaseStatGsonLoader(MinecraftServer server) {
        try {
            files = Files.walk(Paths.get(baseStatDir)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clearMap();
        setStats();
    }

    private void clearMap() {
        ENTITY_INIT_STATS.clear();
    }

    private void setStats() {
        for (Path path : files) {
            EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);
            try (JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(path.toFile())))) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

                if (json.has("isBased") && !json.get("isBased").getAsBoolean()) {
                    continue;
                }

                JsonObject stats = json.get("stats").getAsJsonObject();

                for (String key : stats.keySet()) {
                    JsonObject statObj = stats.get(key).getAsJsonObject();
                    StatTypes statType = StatTypes.getStatByName(key);

                    if (statObj.size() == REQUIRED_TO_FLAT_STAT) {
                        float flatValue = statObj.get(StatSubTag.FLAT.name()).getAsFloat();
                        float percentValue = statObj.get(StatSubTag.PERCENT.name()).getAsFloat();
                        float multiValue = statObj.get(StatSubTag.MULTIPLIER.name()).getAsFloat();

                        map.put(statType, new FloatStat(flatValue, percentValue, multiValue));
                    } else if (statObj.has(StatSubTag.PERCENT.name())) {
                        float percentValue = statObj.get(StatSubTag.PERCENT.name()).getAsFloat();

                        map.put(statType, new PercentStat(percentValue));
                    } else {
                        debugLogger.error("Stat {} is not valid. Check this json. Path : {}", key, path.toString());
                    }
                }

                ENTITY_INIT_STATS.put(new Identifier(json.get("id").toString()), map);
            } catch (IOException e) {
                debugLogger.error("Failed to read file: {}", path, e);
            }
        }
    }
}
