package net.biryeongtrain06.stat_system.stat;

import net.biryeongtrain06.stat_system.component.NonMagicWeaponComponentInterface;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;

import static net.biryeongtrain06.stat_system.stat.PlayerStat.HEALTH_KEY;

public class NonMagicWeaponItemStatSystem extends NonMagicWeaponItemStat implements NonMagicWeaponComponentInterface {

    private final Item item;
    public NonMagicWeaponItemStatSystem(String element, int level, int health, int mana, int attack_damage, int strength, int dexterity, int intelligence, int luck, Item item) {
        super(element, level, health, mana, attack_damage, strength, dexterity, intelligence, luck);
        this.item = item;
    }

    public NonMagicWeaponItemStatSystem(Item item) {
        super("Physical", 1, 5, 2, 3, 0,0,0,0);
        this.item = item;
    }

    @Override
    public int getAttack_damage() {
        return this.attack_damage;
    }

    @Override
    public void addAttack_damage(int attack_damage) {
        this.attack_damage += attack_damage;
    }

    @Override
    public void setAttack_damage(int attack_damage) {
        this.attack_damage = attack_damage;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.element = tag.getString(ELEMENT_KEY);
        this.equipment_level = tag.getInt(EQUIPMENT_LEVEL_KEY);
        this.health = tag.getInt(ITEM_HEALTH_KEY);
        this.mana = tag.getInt(ITEM_MANA_KEY);
        this.strength = tag.getInt(ITEM_STRENGTH_KEY);
        this.dexterity = tag.getInt(ITEM_DEXTERITY_KEY);
        this.intelligence = tag.getInt(ITEM_INTELLIGENCE_KEY);
        this.luck = tag.getInt(ITEM_LUCK_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString(ELEMENT_KEY, this.element);
        tag.putInt(EQUIPMENT_LEVEL_KEY, this.equipment_level);
        tag.putInt(ITEM_HEALTH_KEY, this.health);
        tag.putInt(ITEM_MANA_KEY, this.mana);
        tag.putInt(ITEM_STRENGTH_KEY, this.strength);
        tag.putInt(ITEM_DEXTERITY_KEY, this.dexterity);
        tag.putInt(ITEM_INTELLIGENCE_KEY, this.intelligence);
        tag.putInt(ITEM_LUCK_KEY, this.luck);
    }
}
