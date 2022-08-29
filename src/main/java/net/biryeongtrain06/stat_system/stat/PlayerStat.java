package net.biryeongtrain06.stat_system.stat;

public class PlayerStat {

    protected static final String HEALTH_KEY = "health";
    protected static final String DEFENSE_KEY = "defense";
    protected static final String DODGE_KEY = "dodge";
    protected static final String MANA_KEY = "mana";
    protected static final String MAGIC_DAMAGE_KEY = "magic_damage";
    protected static final String ATTACK_DAMAGE_KEY = "attack_damage";
    protected static final String XP_KEY = "xp";
    protected static final String LEVEL_KEY = "level";
    protected static final String STRENGTH_KEY = "strength";
    protected static final String Dexterity_KEY = "dexterity";
    protected static final String INTELLIGENCE_KEY = "intelligence";
    protected static final String LUCK_KEY = "luck";
    protected static final String STAT_POINT_KEY = "stat_point";

    protected int health;
    protected int defense;
    protected int dodge;
    protected int mana;
    protected double magic_damage;
    protected double attack_damage;
    protected int xp;
    protected int level;
    protected int strength;
    protected int dexterity;
    protected int intelligence;
    protected int luck;
    protected int statPoint;

    public PlayerStat(int xp, int level, int statPoint, int strength, int dexterity, int intelligence, int luck, int health, int defense, int dodge, int mana, double magic_damage, double attack_damage) {
        this.xp = xp;
        this.level = level;
        this.statPoint = statPoint;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.luck = luck;
        this.health = health;
        this.defense = defense;
        this.dodge = dodge;
        this.mana = mana;
        this.magic_damage = magic_damage;
        this.attack_damage = attack_damage;
    }

    public int getStatPoint() {return this.statPoint;}
    public int getMaxHealth() { return this.health; }

    public int getDefense() {return defense;}

    public int getDodge() {return dodge;}

    public int getMana() {return mana;}

    public double getMagic_damage() {return magic_damage;}

    public double getAttack_damage() {return attack_damage;}

    public int getXp() {return xp;}

    public int getLevel() {return level;}

    public int getStrength() {return strength;}

    public int getDexterity() {return dexterity;}

    public int getIntelligence() {
        return intelligence;
    }

    public int getLuck() {
        return luck;
    }
}
