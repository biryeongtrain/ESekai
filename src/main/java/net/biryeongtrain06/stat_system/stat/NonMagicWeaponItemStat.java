package net.biryeongtrain06.stat_system.stat;

public class NonMagicWeaponItemStat extends CommonItemStat{

    protected static String ITEM_ATTACK_DAMAGE_KEY = "item_attack_damage";
    protected int attack_damage;

    NonMagicWeaponItemStat(String element, int level, int health, int mana, int attack_damage, int strength, int dexterity, int intelligence, int luck) {
        super(element, level, health, mana, strength, dexterity, intelligence, luck);
        this.attack_damage = attack_damage;
    }
    public int getAttack_damage() {
        return this.attack_damage;
    }
}
