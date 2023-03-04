package net.biryeongtrain06.qf_stat_mod.utils.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class MobBaseStatLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public MobBaseStatLoader() {
        super(new Gson(), "base_stat");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("base_stat");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        prepared.forEach((id, element) -> {
            JsonObject jsonObject = element.getAsJsonObject();
            Identifier entityId = new Identifier(jsonObject.get("id").toString());


        });
    }
}
