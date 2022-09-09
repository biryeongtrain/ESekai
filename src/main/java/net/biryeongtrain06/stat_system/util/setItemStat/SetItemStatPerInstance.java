package net.biryeongtrain06.stat_system.util.setItemStat;

import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.StatRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

import java.util.ArrayList;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_ATTACK_DAMAGE_KEY;
// TODO : 아이템 NBT 넣는 작업하기

public class SetItemStatPerInstance {

    public SetItemStatPerInstance (ItemStack item, int level, int rarity) {
        StatRegistry[] list = new StatRegistry[rarity];
        if (item.getItem() instanceof SwordItem) {
            ArrayList<String> statlist = ItemStatKeys.getSTAT_LIST();
            statlist.add(ITEM_ATTACK_DAMAGE_KEY);
            for (int i = 0; i < list.length; i++) {
                String s = statlist.get((int) (Math.random() * statlist.size()));
                list[i] = new StatRegistry().decideStat(s);
            }
            ItemRegister(item, level, rarity, list);
        }
    }
// TODO - Element 넣어야함
    public static void ItemRegister(ItemStack item, int level, int rarity, StatRegistry[] list) {
        NbtCompound itemNBT = item.getOrCreateNbt();
        NbtList nbt = new NbtList();
        NbtList lore = new NbtList();
        if (itemNBT.contains("Lore")) {
            lore = itemNBT.getList("Lore", NbtElement.STRING_TYPE);
        }
        for (int i = 0; i < list.length; i++) {
            NbtCompound result = list[i].setStat(item.getNbt(), level, rarity);
            nbt.add(result);
            lore.add(NbtString.of(Text.Serializer.toJson(Text.of(result.getString("name") +" : " + result.getInt("value")))));
        }
        itemNBT.put("Lore", lore);
        itemNBT.put("stat", nbt);
        item.setNbt(itemNBT);
    }
}
