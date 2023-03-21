package net.biryeongtrain06.qf_stat_mod.utils.builder;

import net.minecraft.server.MinecraftServer;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.clearEntityStatMap;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class CommonStatJsonLoader {
    private static IStatGsonLoader baseStatLoader;
    private static IStatGsonLoader customStatLoader;
    public static void setJsonData(MinecraftServer server) {

        baseStatLoader = new BaseStatGsonLoader();
        customStatLoader = new CustomStatGsonLoader();

        clearEntityStatMap();
        baseStatLoader.setStats();
        customStatLoader.setStats();
        debugLogger.info("ended");
    }
}
