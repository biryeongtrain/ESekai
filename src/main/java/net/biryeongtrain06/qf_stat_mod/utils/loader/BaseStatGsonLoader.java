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

public class BaseStatGsonLoader implements IStatGsonLoader{

    private final List<File> baseStatFiles;


    public BaseStatGsonLoader() {
        File baseStatFolder = new File(getDir());
        baseStatFiles = Arrays.stream(baseStatFolder.listFiles()).toList();
    }


    @Override
    public String getDir() {
        return MOD_DIR + "/" + MOD_ID + "/stat/base_stat";
    }

    @Override
    public void setStats() {
        for (File file : baseStatFiles) {
            EnumMap<StatTypes, IStats> map;
            try (JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file)))) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                if (!json.has("type") || !json.get("type").getAsString().equals("stat_data")) continue;
                if (!json.has("isBased") || !json.get("isBased").getAsBoolean()) {
                    continue;
                }

                JsonObject stats = json.get("stats").getAsJsonObject();

                map = makeMap(stats, file);
                if (map == null) continue;

                ENTITY_INIT_STATS.put(new Identifier(json.get("id").getAsString()), map);
            } catch (IOException e) {
                debugLogger.error("Failed to read file: {}", file, e);
            }
        }
    }
}
