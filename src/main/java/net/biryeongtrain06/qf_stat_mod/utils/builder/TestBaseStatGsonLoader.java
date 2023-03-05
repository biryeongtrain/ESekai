package net.biryeongtrain06.qf_stat_mod.utils.builder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.*;

public class TestBaseStatGsonLoader {
    public static Object2ObjectOpenHashMap<Identifier, EnumMap<StatTypes, IStats>> ENTITY_INIT_STATS = new Object2ObjectOpenHashMap<>();
    private final int REQUIRED_TO_FLAT_STAT = StatSubTag.values().length;
    public final String baseStatDir = MOD_DIR + "/main/resources/data/" + MOD_ID + "/test_data/base";

    private final List<File> files;

    public TestBaseStatGsonLoader(MinecraftServer server) {
        File folder = new File(baseStatDir);
        files = Arrays.stream(folder.listFiles()).toList();
        clearMap();
        setStats();
    }

    private void clearMap() {
        ENTITY_INIT_STATS.clear();
    }

    private void setStats() {
        for (File file : files) {
            EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);
            try (JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file)))) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

                if (!json.has("isBased") || !json.get("isBased").getAsBoolean()) {
                    continue;
                }

                JsonObject stats = json.get("stats").getAsJsonObject();

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

                ENTITY_INIT_STATS.put(new Identifier(json.get("id").getAsString()), map);
            } catch (IOException e) {
                debugLogger.error("Failed to read file: {}", file, e);
            }
        }
    }
}
