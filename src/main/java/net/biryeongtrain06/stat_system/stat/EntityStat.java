package net.biryeongtrain06.stat_system.stat;

public class EntityStat {

    protected static final String HEALTH_KEY = "health";
    protected static final String DEFENSE_KEY = "defense";
    protected static final String DODGE_KEY = "dodge";
    protected static final String MANA_KEY = "mana";
    protected static final String MAGIC_DAMAGE_KEY = "magic_damage";
    protected static final String ATTACK_DAMAGE_KEY = "attack_damage";
    protected static final String XP_KEY = "xp";
    protected static final String LEVEL_KEY = "level";

    protected int health;
    protected int defense;
    protected int dodge;
    protected int mana;
    protected int magic_damage;
    protected int attack_damage;
    protected int xp;
    protected int level;

    public EntityStat(int health, int defense, int dodge, int mana, int magic_damage, int attack_damage, int xp, int level) {
        this.health = health;
        this.defense = defense;
        this.dodge = dodge;
        this.mana = mana;
        this.magic_damage = magic_damage;
        this.attack_damage = attack_damage;
        this.xp = xp;
        this.level = level;
    }

    public int getHealth() { return this.health; }

    public int getDefense() {return defense;}

    public int getDodge() {return dodge;}

    public int getMana() {return mana;}

    public int getMagic_damage() {return magic_damage;}

    public int getAttack_damage() {return attack_damage;}

    public int getXp() {return xp;}

    public int getLevel() {return level;}
}
