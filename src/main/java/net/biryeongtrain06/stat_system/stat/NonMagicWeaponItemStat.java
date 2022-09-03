package net.biryeongtrain06.stat_system.stat;

public class NonMagicWeaponItemStat extends CommonItemStat{

    protected int attack_damage;

    NonMagicWeaponItemStat(int health, int mana, int attack_damage, int strength, int dexterity, int intelligence, int luck) {
        this.health = health;
        this.mana = mana;
        this.attack_damage = attack_damage;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.luck = luck;
    }
}
