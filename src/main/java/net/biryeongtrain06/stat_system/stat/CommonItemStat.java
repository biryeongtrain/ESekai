package net.biryeongtrain06.stat_system.stat;

public class CommonItemStat {
    protected static final String ELEMENT_KEY = "element";
    protected static final String EQUIPMENT_LEVEL_KEY = "item_level";

    protected int health;
    protected int mana;
    protected int strength;
    protected int dexterity;
    protected int intelligence;
    protected int luck;
    protected String element;
    protected int equipment_level;

    public String getElement() {return this.element;}

    public int getEquipment_level() {return this.equipment_level;}

    public int getMana() {return this.mana;}

    public int getStrength() {return this.strength;}

    public int getDexterity() {return this.dexterity;}

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getLuck() {
        return this.luck;
    }
}
