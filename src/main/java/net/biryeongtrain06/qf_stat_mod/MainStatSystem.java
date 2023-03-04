package net.biryeongtrain06.qf_stat_mod;


import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.biryeongtrain06.qf_stat_mod.components.ICommonEntityComponents;
import net.biryeongtrain06.qf_stat_mod.utils.builder.TestBaseStatGsonLoader;
import net.biryeongtrain06.qf_stat_mod.utils.builder.TestDataLoader;
import net.biryeongtrain06.qf_stat_mod.utils.data.MobLevelDataLoader;
import net.biryeongtrain06.qf_stat_mod.utils.data.MobXpDataLoader;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemCallbacks;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemGameRules;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;


public class MainStatSystem implements ModInitializer {

    public static final String MOD_ID ="qf_stat_mod";
    final public static Logger debugLogger = LogManager.getLogger("ESekai Debug");

    public static final ComponentKey<ICommonEntityComponents> ENTITY_MODIFIERS = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "entity_modifiers"), ICommonEntityComponents.class);

    public static final String MOD_DIR = FabricLoader.getInstance().getGameDir().toString().replace("run", "src");
    @Override
    public void onInitialize() {
        QfStatSystemCallbacks.init();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MobXpDataLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MobLevelDataLoader());
        QfStatSystemGameRules.setupGameRule();
        QfStatSystemDamageSources.init();

        ServerLifecycleEvents.SERVER_STARTED.register(TestDataLoader::loadTest);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((x, y, z) -> TestDataLoader.loadTest(x));
        ServerLifecycleEvents.SERVER_STARTED.register(TestBaseStatGsonLoader::new);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((x, y, z) -> new TestBaseStatGsonLoader(x));
    }

    public static HashMap<StatTypes, Integer> getEntityDefensiveMap(ComponentProvider provider) {
        return ENTITY_MODIFIERS.get(provider).getDefensiveMap();
    }
}


