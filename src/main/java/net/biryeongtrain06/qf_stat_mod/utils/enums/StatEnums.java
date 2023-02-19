package net.biryeongtrain06.qf_stat_mod.utils.enums;


import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.SubStatTag.*;

public enum StatEnums {

    HEALTH("health", Formatting.RED, RESOURCE, true),
    MANA("mana", Formatting.BLUE, RESOURCE, true),
    ARMOR("armor", Formatting.GRAY, DEFENSIVE, true),
    DODGE("dodge", Formatting.GREEN, DEFENSIVE, true),
    FIRE_RESISTANCE("fire_resistance", Formatting.RED, DEFENSIVE, true),
    WATER_RESISTANCE("water_resistance", Formatting.AQUA, DEFENSIVE, true),
    EARTH_RESISTANCE("earth_resistance", Formatting.GREEN, DEFENSIVE, true),
    LIGHT_RESISTANCE("light_resistance", Formatting.LIGHT_PURPLE, DEFENSIVE, true),
    DARK_RESISTANCE("dark_resistance", Formatting.DARK_PURPLE, DEFENSIVE, true),
    STRENGTH("strength", Formatting.RED, PERK, false),
    DEXTERITY("dexterity", Formatting.GREEN, PERK, false),
    WISDOM("wisdom", Formatting.LIGHT_PURPLE, PERK, false),
    SELECT_POINT("select_point", Formatting.WHITE, PERK, false);
    public final String name;
    public final Text translatableName;
    public final Formatting format;
    public final SubStatTag tag;
    public final Boolean isPlayerStat;
    StatEnums(String name, Formatting format, SubStatTag tag, boolean isPlayerStat) {
        this.name = name;
        this.translatableName = Text.translatable(TextHelper.createTranslation(name));
        this.format = format;
        this.tag = tag;
        this.isPlayerStat = isPlayerStat;
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
        return this.isPlayerStat;
    }

    public SubStatTag getTag() {
        return tag;
    }

    public boolean isPlayerStat(StatEnums val) {
        return val.isPlayerStat;
    }


    public static StatEnums[] getDefensiveStats(boolean isPlayer) {
            List<StatEnums> val = new ArrayList<>();
        for (StatEnums value : values()) {
            if (value.getTag() == DEFENSIVE && value.isPlayerStat == !isPlayer) {
                val.add(value);
            }
        }
        return val.toArray(StatEnums[]::new);

    }
}


