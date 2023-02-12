package net.biryeongtrain06.qf_stat_mod.utils.enums;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums.*;

public enum Elements {
    PHYSICAL("element_physical", ARMOR, 20),
    FIRE("element_fire", FIRE_RESISTANCE, 20),
    WATER("element_water", WATER_RESISTANCE, 20),
    EARTH("element_earth", EARTH_RESISTANCE, 20),
    LIGHT("element_light", LIGHT_RESISTANCE, 10),
    DARK("element_dark", DARK_RESISTANCE, 10);

    final Text translatableName;
    final Identifier i;
    final StatEnums defensiveStat;
    final int spawnPercent;
    Elements(String i, StatEnums defensiveStat, int spawnPercent) {
        this.i = TextHelper.getId(i);
        this.translatableName = Text.translatable(i);
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

    public Text getTranslatableName() {
        return translatableName;
    }

    public Identifier getId() {
        return i;
    }

    public StatEnums getDefensiveStat() {
        return defensiveStat;
    }

    public int getSpawnPercent() {
        return spawnPercent;
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
