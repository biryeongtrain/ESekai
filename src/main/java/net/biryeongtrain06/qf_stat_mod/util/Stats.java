package net.biryeongtrain06.qf_stat_mod.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public enum Stats {

    Health("health", Formatting.RED, "health", "item_health"),
    Defense("defense", Formatting.WHITE, "defense", "item_defense"),
    Dodge("dodge", Formatting.GRAY, "dodge", "item_dodge"),
    Mana("mana", Formatting.BLUE, "mana", "item_mana"),
    Attack_Damage("attack_damage", Formatting.GOLD, "attack_damage", "item_attack_damage"),
    Magic_Damage("magic_damage", Formatting.DARK_AQUA, "magic_damage", "item_magic_damage"),
    Strength("strength", Formatting.WHITE, "strength", "item_strength"),
    Dexterity("dexterity", Formatting.WHITE, "dexterity", "item_dexterity"),
    Intelligence("intelligence", Formatting.WHITE, "intelligence", "item_intelligence"),
    Luck("luck", Formatting.WHITE, "luck", "item_luck"),
    Fire_Resistance("fire_resistance", Formatting.RED, "fire_resistance", "item_fire_resistance"),
    Water_Resistance("water_resistance", Formatting.AQUA, "water_resistance", "item_water_resistance"),
    Earth_Resistance("earth_resistance", Formatting.GREEN, "earth_resistance", "item_earth_resistance"),
    Light_Resistance("light_resistance", Formatting.LIGHT_PURPLE, "light_resistance", "item_light_resistance"),
    Dark_Resistance("dark_resistance", Formatting.DARK_PURPLE, "dark_resistance", "item_dark_resistance");

    Stats(String displayName, Formatting format, String key, String itemKey) {
        this.displayName = Text.translatable(MOD_ID + "." + displayName + "_name");
        this.format = format;
        this.key = key;
        this.itemKey = itemKey;
    }

    public Text displayName;
    public String key;
    public String itemKey;
    public Formatting format;

    public static Stats getStats(String itemKey) {
        for (Stats stats : Stats.values()) {
            if (itemKey.equals(stats.itemKey)) {
                return stats;
            }
        }
        return null;
    }
}
