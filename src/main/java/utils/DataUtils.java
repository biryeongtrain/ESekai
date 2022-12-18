package utils;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class DataUtils {
    private static HashMap<Identifier, Integer> xpModifier = new HashMap<>();

    public static void setXpModifier(JsonObject xp) {
        HashMap<Identifier, Integer> xpMap = new HashMap<>();
        xp.keySet().forEach(keyStr -> {
            String[] s = keyStr.split(":");
            xpMap.put(new Identifier(s[0], s[1]), xp.get(keyStr).getAsInt());
        });
        xpModifier = xpMap;
        debugLogger.info("xpModifier has Successfully Loaded!");
    }

    public static HashMap<Identifier, Integer> getXpModifier() {
        return xpModifier;
    }
}
