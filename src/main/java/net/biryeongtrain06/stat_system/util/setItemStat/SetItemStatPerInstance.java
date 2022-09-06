package net.biryeongtrain06.stat_system.util.setItemStat;

import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.HealthRegistry;
import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.StatRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.ArrayList;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_ATTACK_DAMAGE_KEY;
// TODO : 아이템 NBT 넣는 작업하기

public class SetItemStatPerInstance {

    public static void SetItemStatPerInstance (ItemStack item, int level, int rarity) {
        StatRegistry[] list = new StatRegistry[rarity];
        if (item.getItem() instanceof SwordItem) {
            ArrayList<String> statlist = ItemStatKeys.getSTAT_LIST();
            statlist.add(ITEM_ATTACK_DAMAGE_KEY);
            for (int i = 0; i < list.length; i++) {
                String
                list[i] = new StatRegistry().decideStat(item.getNbt(), level, rarity);
            }
            ItemRegister(item, level, rarity, list);
        }
    }

    public static void ItemRegister(ItemStack item, int level, int rarity, StatRegistry[] list) {
        //item.setNbt(list[0].setStat(item.getNbt(), level, rarity));
    }
}
