package net.biryeongtrain06.stat_system.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.biryeongtrain06.stat_system.MainStatSystem.MOD_ID;

public enum Stats {

    Health("health", Formatting.RED, "health"),
    Defense("defense", Formatting.WHITE, "defense"),
    Dodge("dodge", Formatting.GRAY, "dodge"),
    Mana("mana", Formatting.BLUE, "mana"),
    Attack_Damage("attack_damage", Formatting.GOLD, "attack_damage"),
    Magic_Damage("magic_damage", Formatting.DARK_AQUA, "magic_damage"),
    Strength("strength", Formatting.WHITE, "strength"),
    Dexterity("dexterity", Formatting.WHITE, "dexterity"),
    Intelligence("intelligence", Formatting.WHITE, "intelligence"),
    Luck("luck", Formatting.WHITE, "luck"),
    Fire_Resistance("fire_resistance", Formatting.RED, "fire_resistance"),
    Water_Resistance("water_resistance", Formatting.AQUA, "water_resistance"),
    Earth_Resistance("earth_resistance", Formatting.GREEN, "earth_resistance"),
    Light_Resistance("light_resistance", Formatting.LIGHT_PURPLE, "light_resistance"),
    Dark_Resistance("dark_resistance", Formatting.DARK_PURPLE, "dark_resistance");

    Stats(String displayName, Formatting format, String key) {
        this.displayName = Text.translatable(MOD_ID + "."+displayName+"_name");
        this.format = format;
        this.key = key;
    }
    public Text displayName;
    public String key;
    public Formatting format;
}
