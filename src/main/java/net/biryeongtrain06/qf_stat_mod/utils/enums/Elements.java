package net.biryeongtrain06.qf_stat_mod.utils.enums;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.util.Identifier;

public enum Elements {
    PHYSICAL(TextHelper.getId("element_physical")),
    FIRE(TextHelper.getId("element_fire")),
    WATER(TextHelper.getId("element_water")),
    EARTH(TextHelper.getId("element_earth")),
    LIGHT(TextHelper.getId("element_light")),
    DARK(TextHelper.getId("element_dark"));

    final Identifier i;
    Elements(Identifier i) {
        this.i = i;
    }
}
