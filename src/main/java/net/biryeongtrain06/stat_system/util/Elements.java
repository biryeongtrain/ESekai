package net.biryeongtrain06.stat_system.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public enum Elements {
    Physical("Physical", Formatting.GOLD, "physical", "\u2726"),
    Fire( "Fire", Formatting.RED, "fire", "\u2600"),
    Water("Water", Formatting.AQUA, "water", "\u2749"),
    Earth( "Earth", Formatting.DARK_GREEN, "earth", "\u273F"),

    Elemental("Elemental", Formatting.LIGHT_PURPLE, "elemental", "\u269C"),
    All("All", Formatting.LIGHT_PURPLE, "all", "\u273F");

    Elements(String dmgName, Formatting format, String guidName, String icon) {

        this.dmgName = dmgName;
        this.format = format;
        this.guidName = guidName;
        this.icon = icon;
    }

    public String dmgName;
    public String guidName;
    public String icon;

    public Formatting format;

    public String getIconNameDMG() {
        return getIconNameFormat(dmgName) + "데미지";
    }

    public String getIconNameFormat() {
        return getIconNameFormat(dmgName);
    }

    public String getIconNameFormat(String s) {
        return this.format + this.icon + " " + s + Formatting.GRAY;
    }

    public boolean isPhysical() {
        return this == Physical;
    }

    public boolean isFire() {
        return this == Fire;
    }

    public boolean isElemental() {
        return this != Physical;
    }

    public boolean isWater() {
        return this == Water;
    }

    public boolean isNature() {
        return this == Earth;
    }
    private static List<Elements> allIncludingPhys = Arrays.asList(Physical, Fire, Water, Earth);
    private static List<Elements> allExcludingPhys = Arrays.asList(Fire, Water, Earth, Elemental);
    private static List<Elements> allSingleElementals = Arrays.asList(Fire, Water, Earth);

    public static List<Elements> getAllSingleElementals() {
        return allSingleElementals;
    }

    public static List<Elements> getAllSingleIncludingPhysical() {
        return allIncludingPhys;
    }

    public static List<Elements> getEverythingBesidesPhysical() {
        return allExcludingPhys;
    }

    public boolean elementsMatch(Elements other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (other == All || this == All) {
            return true;
        }

        if (other == Elements.Elemental) {
            if (this != Elements.Physical) {
                return true;
            }
        }
        if (this == Elements.Elemental) {
            if (other != Elements.Physical) {
                return true;
            }
        }

        return false;
    }
}
