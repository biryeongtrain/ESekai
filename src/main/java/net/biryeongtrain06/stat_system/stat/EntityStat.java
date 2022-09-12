package net.biryeongtrain06.stat_system.stat;

public class EntityStat {

    protected static final String MOB_DIFFICULTY_KEY = "difficulty";

    int health;
    int defense;
    int dodge;
    float attack_damage;
    int level;
    int difficulty;
    protected int fire_resistance;
    protected int water_resistance;
    protected int nature_resistance;
    protected int light_resistance;
    protected int dark_resistance;

    public EntityStat(int health, int defense, int dodge, int attack_damage, int level, int difficulty, int fire_resistance, int water_resistance, int nature_resistance, int light_resistance, int dark_resistance) {
        this.health = health;
        this.defense = defense;
        this.dodge = dodge;
        this.attack_damage = attack_damage;
        this.level = level;
        this.difficulty = difficulty;
        this.fire_resistance = fire_resistance;
        this.water_resistance = water_resistance;
        this.nature_resistance = nature_resistance;
        this.light_resistance = light_resistance;
        this.dark_resistance = dark_resistance;
    }

    int getHealth() {
        return this.level;
    }

    int getDefense() {
        return this.defense;
    }

    int getDodge() {
        return this.dodge;
    }

    int getLevel() {
        return this.level;
    }

    float getAttack_damage() {
        return this.attack_damage;
    }

    int getDifficulty() {
        return this.difficulty;
    }

    public int getFireResistance() {return fire_resistance;}

    public int getWaterResistance() {return water_resistance;}

    public int getNatureResistance() {return nature_resistance;}

    public int getLightResistance() {return light_resistance;}

    public int getDarkResistance() { return dark_resistance; }
}
