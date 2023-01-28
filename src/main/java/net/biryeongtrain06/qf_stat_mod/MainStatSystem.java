package net.biryeongtrain06.qf_stat_mod;


import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.biryeongtrain06.qf_stat_mod.components.ICommonEntityComponents;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemGameRules;
import net.biryeongtrain06.qf_stat_mod.data.MobLevelDataLoader;
import net.biryeongtrain06.qf_stat_mod.data.MobXpDataLoader;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemCallbacks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainStatSystem implements ModInitializer {

    public static final String MOD_ID ="qf_stat_mod";
    final public static Logger debugLogger = LogManager.getLogger("Qf Stat Debug");

    public static final ComponentKey<ICommonEntityComponents> ENTITY_MODIFIERS = ComponentRegistry.getOrCreate(new Identifier(MOD_ID, "entity_modifier"), ICommonEntityComponents.class);

    @Override
    public void onInitialize() {
        QfStatSystemCallbacks.init();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MobXpDataLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MobLevelDataLoader());
        QfStatSystemGameRules.setupGameRule();
    }
}


