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

    HEALTH("health", Formatting.RED, RESOURCE, false),
    REGEN_HEALTH_PER_SECOND("health_regen", Formatting.RED, RESOURCE, true),
    MANA("mana", Formatting.BLUE, RESOURCE, false),
    REGEN_MANA_PER_SECOND("regen_mana", Formatting.BLUE, RESOURCE, false),
    ARMOR("armor", Formatting.GRAY, DEFENSIVE, true),
    DODGE("dodge", Formatting.GREEN, DEFENSIVE, true),
    FIRE_RESISTANCE("fire_resistance", Formatting.RED, DEFENSIVE, true),
    WATER_RESISTANCE("water_resistance", Formatting.AQUA, DEFENSIVE, true),
    EARTH_RESISTANCE("earth_resistance", Formatting.GREEN, DEFENSIVE, true),
    LIGHT_RESISTANCE("light_resistance", Formatting.LIGHT_PURPLE, DEFENSIVE, true),
    DARK_RESISTANCE("dark_resistance", Formatting.DARK_PURPLE, DEFENSIVE, true),
    BONUS_MELEE_DAMAGE("bonus_melee_damage", Formatting.GRAY, OFFENSIVE, true),
    BONUS_MAGIC_DAMAGE("bonus_magic_damage", Formatting.AQUA, OFFENSIVE, true),
    HEAL_EFFICIENT("heal_efficient", Formatting.WHITE, OFFENSIVE, true),
    BONUS_RANGED_DAMAGE("bonus_ranged_damage", Formatting.WHITE, OFFENSIVE, true),
    BONUS_XP("bonus_xp", Formatting.GREEN, RESOURCE, false),
    STRENGTH("strength", Formatting.RED, SUB_STAT, false),
    CONSTITUTION("constitution", Formatting.YELLOW, SUB_STAT, false),
    DEXTERITY("dexterity", Formatting.GREEN, SUB_STAT, false),
    INTELLIGENCE("intelligence", Formatting.AQUA, SUB_STAT, false),
    WISDOM("wisdom", Formatting.LIGHT_PURPLE, SUB_STAT, false),
    CHARISMA("charisma", Formatting.GREEN, SUB_STAT, false),
    SELECT_POINT("select_point", Formatting.WHITE, SYSTEM, false);

    public final String name;
    public final Text translatableName;
    public final Formatting format;
    public final StatTypeTag tag;
    public final Boolean entityCanUseThisStat;

    StatTypes(String name, Formatting format, StatTypeTag tag, boolean entityCanUseThisStat) {
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

    public static Stream<StatTypes> getPlayerStats() {

        return Arrays.stream(values()).filter(e -> e.getTag() == OFFENSIVE || e.getTag() == DEFENSIVE || e.getTag() == SUB_STAT);
    }

    public static StatTypes getStatByName(String s) {
        Optional<StatTypes> optionalStatEnums = Arrays.stream(values()).filter(stat -> stat.getName().equals(s)).findFirst();
        return optionalStatEnums.orElse(null);
    }
}


