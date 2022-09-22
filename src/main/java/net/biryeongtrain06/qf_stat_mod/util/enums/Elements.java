package net.biryeongtrain06.qf_stat_mod.util.enums;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public enum Elements {
    Physical("물리", Formatting.GOLD, "Physical", "\u2726", Stats.Defense, "physical"),
    Fire( "화염", Formatting.RED, "Fire", "\u2600", Stats.Fire_Resistance,"fire"),
    Water("냉기", Formatting.AQUA, "Water", "\u2749", Stats.Water_Resistance, "water"),
    Earth( "대지", Formatting.DARK_GREEN, "Earth", "\u273F", Stats.Earth_Resistance, "earth"),
    Light("빛", Formatting.LIGHT_PURPLE, "Light", "\u269C", Stats.Light_Resistance, "light"),
    Dark("어둠", Formatting.DARK_PURPLE, "Dark", "\u2726", Stats.Dark_Resistance, "dark");

    Elements(String dmgName, Formatting format, String guidName, String icon, Stats resistanceStat, String translationKey) {

        this.dmgName = dmgName;
        this.format = format;
        this.guidName = guidName;
        this.icon = icon;
        this.resistanceStat = resistanceStat;
        this.translationKey = MOD_ID + "." + translationKey;
    }

    public String dmgName;
    public String guidName;
    public String icon;

    public Formatting format;

    public Stats resistanceStat;
    public String translationKey;

    public String getIconNameDMG() {
        return getIconNameFormat(dmgName) + " 데미지";
    }

    public String getIconNameFormat() {
        return getIconNameFormat(dmgName);
    }

    public String getIconNameFormat(String s) {
        return this.format + this.icon + " " + s + Formatting.GRAY;
    }

    public Text getTranslationName() {
        return Text.literal(this.icon + " ").fillStyle(Style.EMPTY.withItalic(false)).formatted(this.format).append(Text.translatable(this.translationKey)).formatted(this.format).append(Text.literal(" ")).append(Text.translatable(MOD_ID + ".damage"));
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
