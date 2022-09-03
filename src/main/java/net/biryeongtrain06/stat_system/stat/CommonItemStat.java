package net.biryeongtrain06.stat_system.stat;

public class CommonItemStat {
    protected static final String ELEMENT_KEY = "element";
    protected static final String EQUIPMENT_LEVEL_KEY = "item_level";
    protected static final String ITEM_HEALTH_KEY = "item_health";
    protected static final String ITEM_MANA_KEY = "item_mana";
    protected static final String ITEM_STRENGTH_KEY = "item_strength";
    protected static final String ITEM_DEXTERITY_KEY = "item_dexterity";
    protected static final String ITEM_INTELLIGENCE_KEY = "item_intelligence";
    protected static final String ITEM_LUCK_KEY = "item_luck";

    protected int health;
    protected int mana;
    protected int strength;
    protected int dexterity;
    protected int intelligence;
    protected int luck;
    protected String element;
    protected int equipment_level;

    public CommonItemStat(String element, int level, int health, int mana, int strength, int dexterity, int intelligence, int luck) {
        this.element = element;
        this.equipment_level = level;
        this.health = health;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.luck = luck;
    }

    public String getElement() {return this.element;}

    public int getEquipment_level() {return this.equipment_level;}

    public int getMana() {return this.mana;}

    public int getStrength() {return this.strength;}

    public int getDexterity() {return this.dexterity;}

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getMaxHealth() {
        return this.health;
    }

    public int getLuck() {
        return this.luck;
    }
}
