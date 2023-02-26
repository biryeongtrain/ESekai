package net.biryeongtrain06.qf_stat_mod.utils.builder;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class TestDataLoader {
    public static Object2ObjectOpenHashMap<Identifier, TestCodecBuilder> TEST = new Object2ObjectOpenHashMap<>();

    private static void clearData() {
        TEST.clear();
    }
    public static void loadTest(MinecraftServer server) {
        clearData();

        for (var res : server.getResourceManager().findResources("test_data", (x) -> x.getPath().endsWith(".json")).entrySet()) {
            var id = new Identifier(res.getKey().getPath().substring("test_data/".length(), res.getKey().getPath().length() - 5));

            try {
                var testCodecBuilder = TestCodecBuilder.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(res.getValue().getReader())).getOrThrow(false, (x) -> {});

                TEST.put(id, testCodecBuilder.getFirst());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
