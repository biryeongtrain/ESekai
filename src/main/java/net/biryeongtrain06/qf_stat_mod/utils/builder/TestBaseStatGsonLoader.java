package net.biryeongtrain06.qf_stat_mod.utils.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_DIR;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class TestBaseStatGsonLoader {
    public static Object2ObjectOpenHashMap<Identifier, EnumMap<StatTypes, IStats>> ENTITY_INIT_STATS = new Object2ObjectOpenHashMap<>();
    private Gson GSON = new GsonBuilder()
            .registerTypeAdapter(IStats.class, new StatDeserializer())
            .create();

    public final String baseStatDir = MOD_DIR + "/resources/data/" + MOD_ID + "/test_data/base";

    private List<Path> files;

    public TestBaseStatGsonLoader() throws IOException {
        files = Files.walk(Paths.get(baseStatDir)).toList();
        clearMap();
    }

    private void clearMap() {
        ENTITY_INIT_STATS.clear();
    }

    private void setStats() {
        var it = files.iterator();
        it.forEachRemaining(path -> {
            GSON.fromJson(JsonParser.parseReader())
        });
    }
}
