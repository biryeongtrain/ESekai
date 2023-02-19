package net.biryeongtrain06.qf_stat_mod.api;

import net.biryeongtrain06.qf_stat_mod.item.ElementHandler;
import net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class ItemStats {
    public static Elements getPlayerItemElement(ServerPlayerEntity player) {
        ElementHandler handler = new ElementHandler(player.getMainHandStack(), player);
        return handler.getElement();
    }

    public static boolean setPlayerItemElement(Elements element, ServerPlayerEntity player) {
        ElementHandler handler = new ElementHandler(player.getMainHandStack(), player);
        return handler.setElement(element);
    }

    public static double getItemDamage(ItemStack stack) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.getItemDamage();
    }

    public static boolean setItemDamage(ItemStack stack, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemDamage(value);

    }
    public static boolean setItemStat(ItemStack stack, StatEnums e, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemStat(e, value);
    }

    public static float getItemStat(ItemStack stack, StatEnums e) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.getItemStat(e);
    }
}
