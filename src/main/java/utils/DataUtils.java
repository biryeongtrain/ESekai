package utils;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class DataUtils {
    private static HashMap<Identifier, Integer> xpModifier = new HashMap<>();

    public static void setXpModifier(JsonObject xp) {
        HashMap<Identifier, Integer> xpMap = new HashMap<>();
        xp.keySet().forEach(keyStr -> {
            String[] s = keyStr.split(":");
            xpMap.put(new Identifier(s[0], s[1]), xp.get(keyStr).getAsInt());
        });
        xpModifier = xpMap;
    }

    public static HashMap<Identifier, Integer> getXpModifier() {
        return xpModifier;
    }
}
