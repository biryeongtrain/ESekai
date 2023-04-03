package net.biryeongtrain06.qf_stat_mod.api;

import net.biryeongtrain06.qf_stat_mod.item.ElementHandler;
import net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ItemStats {
    /**
     * 특정 플레이어가 들고있는 아이템의 원소를 반환합니다. 기본값은 Physical 입니다.
     * @param {@link ServerPlayerEntity} player 원소를 확인할 플레이어
     * @return {@link Elements} 플레이어가 가지고 있는 아이템의 원소를 반환, 만약 아이템이 없을 시 <code>PHYSICAL</code> 을 반환함.
     * @see Elements
     */
    public static Elements getPlayerItemElement(ServerPlayerEntity player) {
        ElementHandler handler = new ElementHandler(player.getMainHandStack(), player);
        return handler.getElement();
    }

    /**
     * 특정 플레이어가 주 손에 들고있는 아이템의 원소를 설정합니다.
     * @param element {@link Elements} 원하는 원소
     * @param player {@link ServerPlayerEntity} 대상 플레이어
     * @return 성공시 <code>true</code>, 실패 시 <code>false</code> 가 반환.
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
     * 아이템의 데미지를 정합니다. 수치는 아이템의 기본 데미지를 반영하지 않습니다. 즉 value 값에 100을 넣고 다이아몬드 도끼(공격력 9)를 넣을 경우 최종 수치는 100이 됩니다.
     * @param stack 대상 아이템의 <code>ItemStack</code> 입니다.
     * @param value 아이템에 적용하려는 데미지의 수치입니다.
     * @return 성공 시 <code>true</code>, 실패 시 <code>false</code>를 반환합니다.
     */
    public static boolean setItemDamage(ItemStack stack, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemDamage(value);

    }

    /**
     * 아이템의 스텟을 결정합니다. 원래 있던 스탯이 있다면 수치를 덮어씁니다.
     * @param stack 스탯을 부여할 아이템입니다.
     * @param type 스텟입니다. {@link StatTypes#getStatByName(String)}을 이용하여 입력 할 수 있습니다.
     * @param tag 태그입니다. {@link StatSubTag}의 내용을 확인해주세요.
     * @param value 수치입니다. 수치는 Percent 또는 Multiplier 는 1이 100% 이며, FLAT은 기본 수치입니다.
     * @return 정상적으로 작동했을 경우 <code>true</code>, 정상적으로 작동하지 못 했을 경우 <code>false</code>를 반환합니다.
     */
    public static boolean setItemStat(ItemStack stack, StatTypes type, StatSubTag tag, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemStat(type, tag, value);
    }

    public static boolean setItemStat(ItemStack stack, StatTypes type, StatSubTag tag, Identifier id, float value) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        return handler.setItemStat(type, tag, id, value);
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
