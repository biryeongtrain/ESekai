package net.biryeongtrain06.qf_stat_mod.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class TextHelper {
    public static String createTranslation(String s) {
        return MOD_ID + "." + s;
    }
    public static Identifier getId(String s) {return new Identifier(MOD_ID, s);}

    public static Text getDeathMessageItemHoverableText(ItemStack stack) {
        MutableText mutableText = Text.empty().append(Text.literal("🗡"));
        if (!stack.isEmpty()) {
            mutableText.formatted(Formatting.RED).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(stack))));
        }
        return mutableText;
    }
}
