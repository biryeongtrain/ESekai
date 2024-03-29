package net.biryeongtrain06.qf_stat_mod.utils.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class MobXpDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {

    public MobXpDataLoader() {
        super(new Gson(), MOD_ID + "_mob_xp");
    }
    @Override
    public Identifier getFabricId() {
        return new Identifier(MOD_ID, "mob_xp");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        manager.findResources("mob", id -> id.getPath().endsWith("xp.json")).forEach((id, resourceRef) -> {
            try {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                ExpHandler.setXpModifier(data);
                debugLogger.info("Successfully loaded mob XP data.");
            } catch (IOException e) {
                debugLogger.error("Error occurred while loading resource {}. {}", id.toString(), e.toString());
            }
        });
        manager.findResources("mob", id -> id.getPath().endsWith("rarity.json")).forEach((id, resourceRef) -> {
            try {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                ExpHandler.setRaritySpawnChance(data);
                debugLogger.info("Successfully loaded mob XP data.");
            } catch (IOException e) {
                debugLogger.error("Error occurred while loading resource {}. {}", id.toString(), e.toString());
            }
        });
    }
}
