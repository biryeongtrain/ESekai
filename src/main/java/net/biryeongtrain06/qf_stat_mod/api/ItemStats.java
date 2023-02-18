package net.biryeongtrain06.qf_stat_mod.api;

import net.biryeongtrain06.qf_stat_mod.utils.ElementHandler;
import net.biryeongtrain06.qf_stat_mod.utils.ItemStatHandler;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
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

    public static double getItemDamage(ItemStack stack) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.getItemDamage();
    }

    public static boolean setItemDamage(ItemStack stack, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemDamage(value);
    }

}
