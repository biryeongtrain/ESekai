package net.biryeongtrain06.stat_system.stat;

import net.biryeongtrain06.stat_system.component.CommonStatItemComponentInterface;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommonItemStatSystem extends CommonItemStat implements CommonStatItemComponentInterface {

    private final ItemStack item;
    public CommonItemStatSystem(String element, int level, int health, int mana, int strength, int dexterity, int intelligence, int luck, ItemStack item) {
        super(element, level, health, mana, strength, dexterity, intelligence, luck);
        this.item = item;
    }

    public CommonItemStatSystem(ItemStack item) {
        super("Physical", 1, 5, 2, 0,0,0,0);
        this.item = item;

    }

    @Override
    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public int getEquipmentLevel() {
        return this.equipment_level;
    }

    @Override
    public void setEquipmentLevel(int level) {
        this.equipment_level = level;
    }

    @Override
    public void addStrength(int strength) {
        this.strength += strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    @Override
    public void addDexterity(int dexterity) {
        this.dexterity += dexterity;
    }

    @Override
    public void addIntelligence(int intelligence) {
        this.intelligence += intelligence;
    }

    @Override
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public void addLuck(int luck) {
        this.luck += luck;
    }

    @Override
    public void setLuck(int luck) {
        this.luck = luck;
    }

    @Override
    public void setMaxHealth(int health) {
        this.health = health;
    }

    @Override
    public void addMaxHealth(int health) {
        this.health += health;
    }

    @Override
    public void setMaxMana(int mana) {
        this.mana = mana;
    }

    @Override
    public void addMaxMana(int mana) {
        this.mana += mana;
    }
}
