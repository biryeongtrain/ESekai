package net.biryeongtrain06.stat_system.util;

import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public enum Elements {
    Physical("물리", Formatting.GOLD, "Physical", "\u2726"),
    Fire( "화염", Formatting.RED, "Fire", "\u2600"),
    Water("냉기", Formatting.AQUA, "Water", "\u2749"),
    Earth( "대지", Formatting.DARK_GREEN, "Earth", "\u273F"),

    Light("빛", Formatting.LIGHT_PURPLE, "Light", "\u269C"),
    Dark("어둠", Formatting.DARK_PURPLE, "Dark", "\u2726");

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
        return getIconNameFormat(dmgName) + " 데미지";
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
    private static List<Elements> allExcludingPhys = Arrays.asList(Fire, Water, Earth, Light);
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

        if (other == Dark || this == Dark) {
            return true;
        }

        if (other == Elements.Light) {
            if (this != Elements.Physical) {
                return true;
            }
        }
        if (this == Elements.Light) {
            if (other != Elements.Physical) {
                return true;
            }
        }

        return false;
    }
}
