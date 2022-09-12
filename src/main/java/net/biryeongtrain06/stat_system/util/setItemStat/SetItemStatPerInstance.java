package net.biryeongtrain06.stat_system.util.setItemStat;

import net.biryeongtrain06.stat_system.util.Elements;
import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.StatRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;

import static net.biryeongtrain06.stat_system.MainStatSystem.debugLogger;
import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_ATTACK_DAMAGE_KEY;
// TODO : 아이템 NBT 넣는 작업하기

public class SetItemStatPerInstance {

    public static void RegisterItemStatPerInstance (ItemStack item, int level, int rarity) {
        StatRegistry[] list = new StatRegistry[rarity];
        if (item.getItem() instanceof SwordItem) {
            ArrayList<String> statlist = ItemStatKeys.getSTAT_LIST();
            statlist.add(ITEM_ATTACK_DAMAGE_KEY);
            for (int i = 0; i < list.length; i++) {
                String s = statlist.get((int) (Math.random() * statlist.size()));
                list[i] = StatRegistry.decideStat(s);
            }
            ItemRegister(item, level, rarity, list);
        }
    }
// TODO - Element 넣어야함
    public static void ItemRegister(ItemStack item, int level, int rarity, StatRegistry[] list) {
        NbtCompound itemNBT = item.getOrCreateNbt();
        NbtList nbt = new NbtList();
        for (int i = 0; i < list.length; i++){
            nbt.add(list[i].setStat(level, rarity));
        }
        debugLogger.info("Merging Elements into ITEM NBT....");
        itemNBT.put("element", NbtString.of(Elements.Physical.dmgName));
        debugLogger.info("Merging STATS into ITEM NBT....");
        itemNBT.put("stat", nbt);
        debugLogger.info("Successfully Stat Set.");
        setLore(item, rarity);
    }
// TODO - NEED TESTING / STACK OVERFLOW ERROR / check if lore successfully inject.
    public static void setLore(ItemStack Item, int rarity) {
        debugLogger.info("Setting Lore...");
        NbtList lore = new NbtList();
        NbtCompound itemNBT = Item.getOrCreateSubNbt("display");
        NbtList stats = Item.getNbt().getList("stat", 10);
        if (itemNBT.contains("Lore")) {
            lore = itemNBT.getList("Lore", NbtElement.STRING_TYPE);
        }
        for (int i = 0; i < rarity; i ++) {
            lore.add(NbtString.of(Text.Serializer.toJson(Text.of(stats.getCompound(i).getString("name") + " : " + stats.getCompound(i).getInt("value"))).formatted(Formatting.GREEN)));
        }
        lore.add(NbtString.of(Text.Serializer.toJson(Text.of("Element : " + Item.getNbt().getString("element"))).formatted(Formatting.AQUA)));
        itemNBT.put("Lore", lore);
    }
}
