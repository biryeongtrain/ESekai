package net.biryeongtrain06.stat_system.util.setItemStat;

import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.HealthRegistry;
import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.StatRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
// TODO : 아이템 NBT 넣는 작업하기

public class setItemStatPerInstance {

    public static void itemRegsiterPerInstance (ItemStack item, int level, int rarity) {
        StatRegistry[] list = new StatRegistry[rarity];
        if (item.getItem() instanceof SwordItem) {
            for (int i = 0; i < list.length; i++) {
                list[i] = new HealthRegistry();
            }
            ItemRegister(item, level, rarity, list);
        }
    }

    public static void ItemRegister(ItemStack item, int level, int rarity, StatRegistry[] list) {
        item.setNbt(list[0].setStat(item.getNbt(), level, rarity));
    }
}
