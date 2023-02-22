package net.biryeongtrain06.qf_stat_mod.api;

import net.biryeongtrain06.qf_stat_mod.item.ElementHandler;
import net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.ItemStack;
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

    // TODO 에러뜸 고쳐야함 (보니까 맵에 사이즈가 2갠데 사이즈 1개인줄알고 캐스팅해서 그러는거같음)
    public static double getItemDamage(ItemStack stack) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.getItemDamage();
    }

    public static boolean setItemDamage(ItemStack stack, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemDamage(value);

    }
    public static boolean setItemStat(ItemStack stack, StatTypes e, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemStat(e, value);
    }

    public static float getItemStat(ItemStack stack, StatTypes e) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.getItemStat(e);
    }
}
