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

public enum StatTypes {

    HEALTH("health", Formatting.RED, RESOURCE, false, false),
    REGEN_HEALTH_PER_SECOND("health_regen", Formatting.RED, RESOURCE, true, false),
    MANA("mana", Formatting.BLUE, RESOURCE, false, false),
    REGEN_MANA_PER_SECOND("mana_regen", Formatting.BLUE, RESOURCE, false, false),
    ARMOR("armor", Formatting.GRAY, DEFENSIVE, true, false),
    DODGE("dodge", Formatting.GREEN, DEFENSIVE, true, true),
    FIRE_RESISTANCE("fire_resistance", Formatting.RED, DEFENSIVE, true, true),
    WATER_RESISTANCE("water_resistance", Formatting.AQUA, DEFENSIVE, true, true),
    EARTH_RESISTANCE("earth_resistance", Formatting.GREEN, DEFENSIVE, true, true),
    LIGHT_RESISTANCE("light_resistance", Formatting.LIGHT_PURPLE, DEFENSIVE, true, true),
    DARK_RESISTANCE("dark_resistance", Formatting.DARK_PURPLE, DEFENSIVE, true, true),
    BONUS_MELEE_DAMAGE("bonus_melee_damage", Formatting.GRAY, OFFENSIVE, false, false),
    BONUS_MAGIC_DAMAGE("bonus_magic_damage", Formatting.AQUA, OFFENSIVE, true, false),
    HEAL_EFFICIENT("heal_efficient", Formatting.WHITE, OFFENSIVE, true, false),
    BONUS_RANGED_DAMAGE("bonus_ranged_damage", Formatting.WHITE, OFFENSIVE, true, true),
    BONUS_XP("bonus_xp", Formatting.GREEN, RESOURCE, false, true),
    STRENGTH("strength", Formatting.RED, SUB_STAT, false, false),
    CONSTITUTION("constitution", Formatting.YELLOW, SUB_STAT, false, false),
    DEXTERITY("dexterity", Formatting.GREEN, SUB_STAT, false, false),
    INTELLIGENCE("intelligence", Formatting.AQUA, SUB_STAT, false, false),
    WISDOM("wisdom", Formatting.LIGHT_PURPLE, SUB_STAT, false, false),
    CHARISMA("charisma", Formatting.GREEN, SUB_STAT, false, false),
    SELECT_POINT("select_point", Formatting.WHITE, SYSTEM, false, false);

    private final String name;
    private final Text translatableName;
    private final Formatting format;
    private final StatTypeTag tag;
    private final Boolean entityCanUseThisStat;
    private final Boolean isOnlyPercent;

    StatTypes(String name, Formatting format, StatTypeTag tag, boolean entityCanUseThisStat, boolean isOnlyPercent) {
        this.name = name;
        this.translatableName = Text.translatable(TextHelper.createTranslation(name));
        this.format = format;
        this.tag = tag;
        this.entityCanUseThisStat = entityCanUseThisStat;
        this.isOnlyPercent = isOnlyPercent;
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

    public boolean isPlayerStat(StatTypes val) {
        return val.entityCanUseThisStat;
    }


    public static StatTypes[] getDefensiveStats(boolean isPlayer) {
            List<StatTypes> val = new ArrayList<>();
        for (StatTypes value : values()) {
            if (value.getTag() == DEFENSIVE && value.entityCanUseThisStat == !isPlayer) {
                val.add(value);
            }
        }
        return val.toArray(StatTypes[]::new);

    }

    public static StatTypes getStatByName(String s) {
        Optional<StatTypes> optionalStatEnums = Arrays.stream(values()).filter(stat -> stat.getName().equals(s)).findFirst();
        return optionalStatEnums.orElse(null);
    }

    public static Stream<StatTypes> getAvailablePlayerStats() {
        return Arrays.stream(values()).filter(e -> e.tag != SYSTEM);
    }
}


