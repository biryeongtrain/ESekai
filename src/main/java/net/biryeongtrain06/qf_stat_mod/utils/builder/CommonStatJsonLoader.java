package net.biryeongtrain06.qf_stat_mod.utils.builder;

import net.minecraft.server.MinecraftServer;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.clearEntityStatMap;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class CommonStatJsonLoader {
    IStatGsonLoader baseStatLoader = new BaseStatGsonLoader();
    IStatGsonLoader customStatLoader = new CustomStatGsonLoader();
    public CommonStatJsonLoader(MinecraftServer server) {
        clearEntityStatMap();
        baseStatLoader.setStats();
        customStatLoader.setStats();
        debugLogger.info("ended");
    }
}
