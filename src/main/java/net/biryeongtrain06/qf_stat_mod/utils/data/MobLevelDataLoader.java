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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class MobLevelDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {


    public MobLevelDataLoader() {
        super(new Gson(), MOD_ID + "_mob_level");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(MOD_ID, "mob_level");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        manager.findResources("mob", id -> id.getPath().endsWith("level.json")).forEach((id, resourceRef) -> {
            try {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                ExpHandler.initLevelModifier(data);
                debugLogger.info("Successfully loaded level modifier data.");
            } catch (Exception e) {
                debugLogger.error("Error occurred while loading resource {}. {}", id.toString(), e.toString());
            }
        });
    }
}
