package net.biryeongtrain06.qf_stat_mod.stat;

public class PlayerStat {

    public static final String HEALTH_KEY = "health";
    public static final String DEFENSE_KEY = "defense";
    public static final String DODGE_KEY = "dodge";
    public static final String MANA_KEY = "mana";
    public static final String MAGIC_DAMAGE_KEY = "magic_damage";
    public static final String ATTACK_DAMAGE_KEY = "attack_damage";
    public static final String XP_KEY = "xp";
    public static final String LEVEL_KEY = "level";
    public static final String STRENGTH_KEY = "strength";
    public static final String Dexterity_KEY = "dexterity";
    public static final String INTELLIGENCE_KEY = "intelligence";
    public static final String LUCK_KEY = "luck";
    public static final String STAT_POINT_KEY = "stat_point";
    public static final String FIRE_RESISTANCE_KEY = "fire_resistance";
    public static final String WATER_RESISTANCE_KEY = "water_resistance";
    public static final String EARTH_RESISTANCE_KEY = "earth_resistance";
    public static final String LIGHT_RESISTANCE_KEY = "light_resistance";
    public static final String DARK_RESISTANCE_KEY = "dark_resistance";

    protected int health;
    protected int fire_resistance;
    protected int water_resistance;
    protected int earth_resistance;
    protected int light_resistance;
    protected int dark_resistance;
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

    public PlayerStat(int xp, int level, int statPoint, int strength, int dexterity, int intelligence, int luck, int health, int defense, int dodge, int mana, double magic_damage, double attack_damage, int fire_resistance, int water_resistance, int earth_resistance, int light_resistance, int dark_resistance) {
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
        this.fire_resistance = fire_resistance;
        this.water_resistance = water_resistance;
        this.earth_resistance = earth_resistance;
        this.light_resistance = light_resistance;
        this.dark_resistance = dark_resistance;
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

    public int getFireResistance() {return fire_resistance;}

    public int getWaterResistance() {return water_resistance;}

    public int getEarthResistance() {return earth_resistance;}

    public int getLightResistance() {return light_resistance;}

    public int getDarkResistance() { return dark_resistance; }
}
