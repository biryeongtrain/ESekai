package net.biryeongtrain06.stat_system.component;

public interface CommonStatItemComponentInterface {

    String getElement();

    void setElement(String element);

    int getEquipmentLevel();

    void setEquipmentLevel(int level);

    //TODO Make Set Method

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

    void setMaxMana(int mana);

    void addMaxMana(int mana);
}
