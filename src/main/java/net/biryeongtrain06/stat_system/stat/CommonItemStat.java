package net.biryeongtrain06.stat_system.stat;

public class CommonItemStat {
    protected static final String ELEMENT_KEY = "element";
    protected static final String EQUIPMENT_LEVEL_KEY = "item_level";

    protected int health;
    protected int defense;
    protected int dodge;
    protected int mana;
    protected double magic_damage;
    protected double attack_damage;
    protected int strength;
    protected int dexterity;
    protected int intelligence;
    protected int luck;

    public int getMaxHealth() { return this.health; }

    public int getDefense() {return defense;}

    public int getDodge() {return dodge;}

    public int getMana() {return mana;}

    public double getMagic_damage() {return magic_damage;}

    public double getAttack_damage() {return attack_damage;}

    public int getStrength() {return strength;}

    public int getDexterity() {return dexterity;}

    public int getIntelligence() {
        return intelligence;
    }

    public int getLuck() {
        return luck;
    }
}
