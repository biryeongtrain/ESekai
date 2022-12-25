package net.biryeongtrain06.qf_stat_mod.utils;

import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class TextHelper {
    public static String createTranslation(String s) {
        return MOD_ID + "." + s;
    }
    public static Identifier getId(String s) {return new Identifier(MOD_ID, s);}
}
