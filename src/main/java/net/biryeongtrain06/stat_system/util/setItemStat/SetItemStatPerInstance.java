package net.biryeongtrain06.stat_system.util.setItemStat;

import net.biryeongtrain06.stat_system.item.registerOnItemCrafted;
import net.biryeongtrain06.stat_system.util.Elements;
import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.AttackDamageRegistry;
import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.StatRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_ELEMENT_KEY;
import static net.biryeongtrain06.stat_system.util.setItemStat.ItemStatKeys.ITEM_ATTACK_DAMAGE_KEY;


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
        if (item.getItem() instanceof SwordItem) {
            ArrayList<String> statlist = ItemStatKeys.getSTAT_LIST();
            statlist.add(ITEM_ATTACK_DAMAGE_KEY);
            for (int i = 0; i < list.length; i++) {
                String s = statlist.get((int) (Math.random() * statlist.size()));
                list[i] = StatRegistry.decideStat(s);
            }
            ItemRegister(item, level, rarity, list, element);
        }
    }

    public static void RegisterItemStatPerInstance (ItemStack item, int level, int rarity) {
        StatRegistry[] list = new StatRegistry[rarity];
        if (item.getItem() instanceof SwordItem) {
            ArrayList<String> statlist = ItemStatKeys.getSTAT_LIST();
            statlist.add(ITEM_ATTACK_DAMAGE_KEY);
            for (int i = 0; i < list.length; i++) {
                String s = statlist.get((int) (Math.random() * statlist.size()));
                list[i] = StatRegistry.decideStat(s);
            }
            ItemRegister(item, level, rarity, list, Elements.Physical);
        }
    }
// TODO - 공격 Attribute 붙으면 데미지 증가하나 체크해야함, Lore 도 잘붙는지 확인
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
        itemNBT.put(ITEM_ELEMENT_KEY, NbtString.of(element.guidName));
        itemNBT.put("stat", nbt);
        setLore(item, rarity);
    }
    public static void setLore(ItemStack Item, int rarity) {
        NbtList lore = new NbtList();
        NbtCompound itemNBT = Item.getOrCreateSubNbt("display");
        NbtList stats = Item.getNbt().getList("stat", 10);
        Elements element = Elements.valueOf(Item.getNbt().getString(ITEM_ELEMENT_KEY));
        if (itemNBT.contains("Lore")) {
            lore = itemNBT.getList("Lore", NbtElement.STRING_TYPE);
        }
        for (int i = 0; i < rarity; i ++) {
            lore.add(NbtString.of(Text.Serializer.toJson(Text.literal(stats.getCompound(i).getString("name") + " : " + stats.getCompound(i).getInt("value")).formatted(Formatting.AQUA))));
        }
        lore.add(NbtString.of(Text.Serializer.toJson(Text.empty())));
        lore.add(NbtString.of(Text.Serializer.toJson(Text.literal(element.getIconNameDMG().formatted(element.format)))));
        itemNBT.put("Lore", lore);
    }
}
