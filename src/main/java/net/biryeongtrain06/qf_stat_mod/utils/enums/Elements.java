package net.biryeongtrain06.qf_stat_mod.utils.enums;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums.*;

public enum Elements {
    PHYSICAL(TextHelper.getId("element_physical"), ARMOR, 20),
    FIRE(TextHelper.getId("element_fire"), FIRE_RESISTANCE, 20),
    WATER(TextHelper.getId("element_water"), WATER_RESISTANCE, 20),
    EARTH(TextHelper.getId("element_earth"), EARTH_RESISTANCE, 20),
    LIGHT(TextHelper.getId("element_light"), LIGHT_RESISTANCE, 10),
    DARK(TextHelper.getId("element_dark"), DARK_RESISTANCE, 10);

    final Identifier i;
    final StatEnums defensiveStat;
    final int spawnPercent;
    Elements(Identifier i, StatEnums defensiveStat, int spawnPercent) {
        this.i = i;
        this.defensiveStat = defensiveStat;
        this.spawnPercent = spawnPercent;
    }

    public static HashMap<Elements, Integer> getPercent() {
        HashMap<Elements, Integer> h = new HashMap<>();
        for(Elements e : values()) {
            h.put(e, e.spawnPercent);
        }
        return h;
    }

    public static HashMap<Elements, Integer> setPeakElement(Elements e) {
        HashMap<Elements, Integer> h = getPercent();
        for(Elements el : values()) {
            h.replace(el, el.spawnPercent / 2);
        }
        if (e == LIGHT || e == DARK) h.replace(e, 55);
        else h.replace(e, 60);
        return h;
    }
}
