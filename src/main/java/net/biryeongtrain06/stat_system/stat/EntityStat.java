package net.biryeongtrain06.stat_system.stat;

public class EntityStat {

    protected static final String HEALTH_KEY = "health";
    protected static final String DEFENSE_KEY = "defense";
    protected static final String DODGE_KEY = "dodge";
    protected static final String ATTACK_DAMAGE_KEY = "attack_damage";
    protected static final String LEVEL_KEY = "level";
    protected static final String DIFFICULTY_KEY = "difficulty";

    int health;
    int defense;
    int dodge;
    int attack_damage;
    int level;
    int difficulty;

    public EntityStat(int health, int defense, int dodge, int attack_damage, int level, int difficulty) {
        this.health = health;
        this.defense = defense;
        this.dodge = dodge;
        this.attack_damage = attack_damage;
        this.level = level;
        this.difficulty = difficulty;
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

    int getAttack_damage() {
        return this.attack_damage;
    }

    int getDifficulty() {
        return this.difficulty;
    }
}
