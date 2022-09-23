package net.biryeongtrain06.qf_stat_mod.util.setItemStat;

import net.biryeongtrain06.qf_stat_mod.item.registerOnItemCrafted;
import net.biryeongtrain06.qf_stat_mod.util.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.util.enums.Rarity;
import net.biryeongtrain06.qf_stat_mod.util.enums.Stats;
import net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry.AttackDamageRegistry;
import net.biryeongtrain06.qf_stat_mod.util.setItemStat.statRegistry.StatRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.UUID;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.ItemStatKeys.*;


public class SetItemStatPerInstance {

    /**
     * 아이템에 부여할 스텟을 랜덤으로 결정 후 ItemRegister 메소드를 호출 해 NBT를 부여하는 메소드입니다.
     * @param item 대상 아이탬입니다.
     * @param level 아이템의 레벨입니다. 스텟 수치에 영향을 끼칩니다.
     * @param rarity 레어도로 부여될 스텟의 갯수와 스텟의 수치에 영향을 끼칩니다.
     * @see registerOnItemCrafted#setRarity() 희귀도 설정 메소드
     */
    public static void RegisterItemStatPerInstance (ItemStack item, int level, int rarity, Elements element) {
        StatRegistry[] list = new StatRegistry[rarity];
        ArrayList<String> statlist = ItemStatKeys.getSTAT_LIST();
        if (item.getItem() instanceof SwordItem) {
            statlist.add(ITEM_ATTACK_DAMAGE_KEY);
        }
        else if (item.getItem() instanceof ArmorItem) {
            statlist.add(ITEM_DARK_RESISTANCE_KEY);
            statlist.add(ITEM_FIRE_RESISTANCE_KEY);
            statlist.add(ITEM_WATER_RESISTANCE_KEY);
            statlist.add(ITEM_EARTH_RESISTANCE_KEY);
            statlist.add(ITEM_LIGHT_RESISTANCE_KEY);
            statlist.add(ITEM_DEFENSE_KEY);
            statlist.add(ITEM_DODGE_KEY);
        }
        for (int i = 0; i < list.length; i++) {
            String s = statlist.get((int) (Math.random() * statlist.size()));
            list[i] = StatRegistry.decideStat(s);
        }
        if (item.getItem() instanceof SwordItem) {
            ItemRegister(item, level, rarity, list, element);
        } else {
            ItemRegister(item, level, rarity, list, Elements.Physical);
        }
    }

    public static void RegisterItemStatPerInstance (ItemStack item, int level, int rarity) {
        StatRegistry[] list = new StatRegistry[rarity];
        ArrayList<String> statlist = ItemStatKeys.getSTAT_LIST();
        if (item.getItem() instanceof SwordItem) {
            statlist.add(ITEM_ATTACK_DAMAGE_KEY);
        }
        else if (item.getItem() instanceof ArmorItem) {
            statlist.add(ITEM_DARK_RESISTANCE_KEY);
            statlist.add(ITEM_FIRE_RESISTANCE_KEY);
            statlist.add(ITEM_WATER_RESISTANCE_KEY);
            statlist.add(ITEM_EARTH_RESISTANCE_KEY);
            statlist.add(ITEM_LIGHT_RESISTANCE_KEY);
            statlist.add(ITEM_DEFENSE_KEY);
            statlist.add(ITEM_DODGE_KEY);

        }
        for (int i = 0; i < list.length; i++) {
            String s = statlist.get((int) (Math.random() * statlist.size()));
            list[i] = StatRegistry.decideStat(s);
            ItemRegister(item, level, rarity, list, Elements.Physical);
        }
    }

    /**
     * NBT결정, 아이템에 병합하는 메소드입니다. 이후 setLore 메소드를 호출하면서 설명값을 부여합니다.
     * @param item 대상 아이템입니다.
     * @param level 아이템의 레벨입니다. 스텟 수치에 영향을 끼칩니다.
     * @param list 스텟 목록입니다.
     **/
    public static void ItemRegister(ItemStack item, int level, int rarity, StatRegistry[] list, Elements element) {
        NbtCompound itemNBT = item.getOrCreateNbt();
        NbtList nbt = new NbtList();
        for (int i = 0; i < list.length; i++){
            nbt.add(list[i].setStat(level, rarity));
            if (list[i] instanceof AttackDamageRegistry) {
                item.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(UUID.randomUUID(), "generic.attack_damage",  nbt.getCompound(i).getInt("value"), EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            }
        }
        if (item.getItem() instanceof SwordItem) {
            itemNBT.put(ITEM_ELEMENT_KEY, NbtString.of(element.guidName));
        }
        itemNBT.putInt("rarity", rarity);
        itemNBT.put("stat", nbt);
        setLore(item, rarity);
    }
    public static void setLore(ItemStack Item, int rarity) {
        NbtList lore = new NbtList();
        NbtCompound itemNBT = Item.getOrCreateSubNbt("display");
        NbtList stats = Item.getNbt().getList("stat", 10);
        if (itemNBT.contains("Lore")) {
            lore = itemNBT.getList("Lore", NbtElement.STRING_TYPE);
        }
        lore.add(NbtString.of(Text.Serializer.toJson(Rarity.getRarityEnum(rarity).getTranslationKey())));
        for (int i = 0; i < rarity; i ++) {
            Stats statEnum = Stats.getStats(stats.getCompound(i).getString("name"));
            lore.add(NbtString.of(Text.Serializer.toJson(statEnum.displayName.copy().fillStyle(Style.EMPTY.withItalic(false)).formatted(statEnum.format).append(Text.literal(" : " + stats.getCompound(i).getInt("value")).formatted(Formatting.RESET).formatted(statEnum.format)))));
        }
        lore.add(NbtString.of(Text.Serializer.toJson(Text.empty())));
        if (Item.getItem() instanceof SwordItem) {
            Elements element = Elements.valueOf(Item.getNbt().getString(ITEM_ELEMENT_KEY));
            lore.add(NbtString.of(Text.Serializer.toJson((element.getTranslationName()))));
        }
        itemNBT.put("Lore", lore);
    }

}
