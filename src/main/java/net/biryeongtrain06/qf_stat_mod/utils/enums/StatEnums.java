package net.biryeongtrain06.qf_stat_mod.utils.enums;


import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypeTag.*;

public enum StatEnums {

    HEALTH("health", Formatting.RED, RESOURCE, true),
    HEALTH_FLAT("health_flat", Formatting.RED, RESOURCE, true),
    HEALTH_INCREASE_PERCENT("health_percent", Formatting.RED, RESOURCE, true),
    HEALTH_INCREASE_MULTI("health_multi", Formatting.RED, RESOURCE, true),
    MANA("mana", Formatting.BLUE, RESOURCE, true),
    ARMOR("armor", Formatting.GRAY, DEFENSIVE, true),
    DODGE("dodge", Formatting.GREEN, DEFENSIVE, true),
    FIRE_RESISTANCE("fire_resistance", Formatting.RED, DEFENSIVE, true),
    WATER_RESISTANCE("water_resistance", Formatting.AQUA, DEFENSIVE, true),
    EARTH_RESISTANCE("earth_resistance", Formatting.GREEN, DEFENSIVE, true),
    LIGHT_RESISTANCE("light_resistance", Formatting.LIGHT_PURPLE, DEFENSIVE, true),
    DARK_RESISTANCE("dark_resistance", Formatting.DARK_PURPLE, DEFENSIVE, true),
    PROJECTILE_DAMAGE_PERCENT("projectile_damage_percent", Formatting.WHITE, OFFENSIVE, true),
    PROJECTILE_DAMAGE_FLAT("projectile_damage_flat", Formatting.WHITE, OFFENSIVE, true),
    PROJECTILE_DAMAGE_MULTI("projectile_damage_multi", Formatting.WHITE, OFFENSIVE, true),
    STRENGTH("strength", Formatting.RED, PERK, false),
    DEXTERITY("dexterity", Formatting.GREEN, PERK, false),
    WISDOM("wisdom", Formatting.LIGHT_PURPLE, PERK, false),
    SELECT_POINT("select_point", Formatting.WHITE, SYSTEM, false);
    public final String name;
    public final Text translatableName;
    public final Formatting format;
    public final StatTypeTag tag;
    public final Boolean entityCanUseThisStat;
    StatEnums(String name, Formatting format, StatTypeTag tag, boolean entityCanUseThisStat) {
        this.name = name;
        this.translatableName = Text.translatable(TextHelper.createTranslation(name));
        this.format = format;
        this.tag = tag;
        this.entityCanUseThisStat = entityCanUseThisStat;
    }

    public String getName() {
        return name;
    }

    public Text getTranslatableName() {
        return translatableName;
    }

    public Formatting getFormat() {
        return format;
    }

    public boolean isPlayerStat() {
        return this.entityCanUseThisStat;
    }

    public StatTypeTag getTag() {
        return tag;
    }

    public boolean isPlayerStat(StatEnums val) {
        return val.entityCanUseThisStat;
    }


    public static StatEnums[] getDefensiveStats(boolean isPlayer) {
            List<StatEnums> val = new ArrayList<>();
        for (StatEnums value : values()) {
            if (value.getTag() == DEFENSIVE && value.entityCanUseThisStat == !isPlayer) {
                val.add(value);
            }
        }
        return val.toArray(StatEnums[]::new);

    }

    public static Stream<StatEnums> getPlayerStats() {

        return Arrays.stream(values()).filter(e -> e.getTag() == OFFENSIVE || e.getTag() == DEFENSIVE || e.getTag() == PERK);
    }

    public static StatEnums getStatByName(String s) {
        Optional<StatEnums> optionalStatEnums = Arrays.stream(values()).filter(stat -> stat.getName().equals(s)).findFirst();
        return optionalStatEnums.orElse(null);
    }
}


