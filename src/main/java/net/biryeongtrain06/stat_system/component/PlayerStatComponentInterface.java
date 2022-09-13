package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface PlayerStatComponentInterface extends Component {

    int getStatPoint();

    void addStatPoint(int statPoint);

    void setStatPoint(int statPoint);
    int getStrength();

    void addStrength(int strength);

    void setStrength(int strength);

    int getDexterity();

    void setDexterity(int dexterity);

    void addDexterity(int dexterity);

    int getIntelligence();

    void addIntelligence(int intelligence);

    void setIntelligence(int intelligence);

    int getLuck();

    void addLuck(int luck);

    void setLuck(int luck);

    int getMaxHealth();

    void setMaxHealth(int health);

    void addMaxHealth(int health);

    int getDefense();

    void setDefense(int defense);

    void addDefense(int defense);

    int getDodge();

    void setDodge(int dodge);

    void addDodge(int dodge);

    int getMana();

    void setMaxMana(int mana);

    void addMaxMana(int mana);

    double getMagic_damage();

    void setMagic_damage(double magic_damage);

    void addMagic_damage(double magic_damage);

    double getAttack_damage();

    void setAttack_damage(double attack_damage);

    void addAttack_damage(double attack_damage);

    int getXp();

    void addXp(int xp);

    void setXp(int xp);

    int getLevel();

    void setLevel(int level);

    void addLevel(int level);

    int getFireResistance();

    void addFireResistance(int value);

    void setFireResistance(int value);

    int  getWaterResistance();

    void addWaterResistance(int value);

    void setWaterResistance(int value);

    int getEarthResistance();

    void addEarthResistance(int value);

    void setEarthResistance(int value);

    int getLightResistance();

    void addLightResistance(int value);

    void setLightResistance(int value);

    int getDarkResistance();

    void addDarkResistance(int value);

    void setDarkResistance(int value);
}
