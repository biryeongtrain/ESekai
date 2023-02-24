package net.biryeongtrain06.qf_stat_mod.api;

import net.biryeongtrain06.qf_stat_mod.item.ElementHandler;
import net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public class ItemStats {
    /**
     * 특정 플레이어가 들고있는 아이템의 원소를 반환합니다. 기본값은 Physical 입니다.
     * @param player
     * @return Elements 플레이어가 가지고 있는 아이템의 원소를 반환, 만약 아이템이 없을 시 PHYSICAL 을 반환함.
     * @see Elements
     */
    public static Elements getPlayerItemElement(ServerPlayerEntity player) {
        ElementHandler handler = new ElementHandler(player.getMainHandStack(), player);
        return handler.getElement();
    }

    /**
     * 특정 플레이어가 주 손에 들고있는 아이템의 원소를 설정합니다.
     * @param element 원하는 원소
     * @param player
     * @return 실패 시 false 가 반환.
     */
    public static boolean setPlayerItemElement(@NotNull Elements element, ServerPlayerEntity player) {
        ElementHandler handler = new ElementHandler(player.getMainHandStack(), player);
        return handler.setElement(element);
    }

    // TODO 에러뜸 고쳐야함 (보니까 맵에 사이즈가 2갠데 사이즈 1개인줄알고 캐스팅해서 그러는거같음)
    public static double getItemDamage(ItemStack stack) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.getItemDamage();
    }

    /**
     *
     * @param stack
     * @param value
     * @return
     */
    public static boolean setItemDamage(ItemStack stack, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemDamage(value);

    }
    public static boolean setItemStat(ItemStack stack, StatTypes e, StatSubTag tag, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemStat(e, tag, value);
    }

    public static boolean addItemStat(ItemStack stack, StatTypes type, StatSubTag tag, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.addItemStat(type, tag, value);
    }

    public static float getItemStat(ItemStack stack, StatTypes e, StatSubTag tag) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.getItemStat(e, tag);
    }
}
