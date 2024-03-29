package net.biryeongtrain06.qf_stat_mod.utils.loader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.*;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class CustomStatGsonLoader implements IStatGsonLoader{

    private final List<File> statFiles;

    public CustomStatGsonLoader() {
        File folder = new File(getDir());
        if (folder.listFiles() == null) {
            statFiles = null;
        } else {
            statFiles = Arrays.stream(folder.listFiles()).toList();
        }
    }

    @Override
    public String getDir() {
        return MOD_DIR + "/" + MOD_ID + "/stat/custom_stat";
    }

    @Override
    public void setStats() {
        if (statFiles.isEmpty()) return;
        for (File file : statFiles) {
            EnumMap<StatTypes, IStats> map;
            try (JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file)))) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                Identifier id = new Identifier(json.get("id").getAsString());
                if (!json.has("type") || !json.get("type").getAsString().equals("stat_data")) continue;
                if (!json.has("isBased") || json.get("isBased").getAsBoolean()) {
                    continue;
                }

                JsonObject stats = json.get("stats").getAsJsonObject();
                map = makeMap(stats, file);
                if (map == null) continue;
                if (!ENTITY_INIT_STATS.containsKey(id)) {
                    ENTITY_INIT_STATS.put(id, map);
                    continue;
                }
                map.keySet().forEach(key -> ENTITY_INIT_STATS.get(id).put(key, map.get(key)));
            } catch (IOException e) {
                debugLogger.error("Failed to read file: {}", file, e);
            }
        }
    }
}
