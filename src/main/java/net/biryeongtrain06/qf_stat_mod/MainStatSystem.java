package net.biryeongtrain06.qf_stat_mod;


import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.components.INewCommonEntityComponents;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.loader.CommonStatJsonLoader;
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

import java.util.EnumMap;


public class MainStatSystem implements ModInitializer {

    public static final String MOD_ID ="qf_stat_mod";
    final public static Logger debugLogger = LogManager.getLogger("ESekai Debug");

    public static final ComponentKey<INewCommonEntityComponents> ENTITY_MODIFIERS = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "entity_modifiers"), INewCommonEntityComponents.class);

    public static final String MOD_DIR = FabricLoader.getInstance().getModContainer(MOD_ID).get().findPath("data").get().normalize().toString();

    public static Object2ObjectOpenHashMap<Identifier, EnumMap<StatTypes, IStats>> ENTITY_INIT_STATS = new Object2ObjectOpenHashMap<>();

    public static void clearEntityStatMap() {
        ENTITY_INIT_STATS.clear();
    }
    @Override
    public void onInitialize() {
        DataStorage.registerAllDataStorage();
        QfStatSystemCallbacks.init();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MobXpDataLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MobLevelDataLoader());
        QfStatSystemGameRules.setupGameRule();
        QfStatSystemDamageSources.init();

        Identifier phase = TextHelper.getId("json_loader");
        ServerLifecycleEvents.SERVER_STARTING.register(phase, CommonStatJsonLoader::setJsonData);
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register(phase, (x, y) -> CommonStatJsonLoader.setJsonData(x));
        FabricLoader.getInstance().getModContainer(MOD_ID);
    }
}


