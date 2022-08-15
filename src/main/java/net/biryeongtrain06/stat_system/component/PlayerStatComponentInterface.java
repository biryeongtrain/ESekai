package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface PlayerStatComponentInterface extends Component {

    int getHealth();

    void setHealth(int health);

    void addHealth(int health);

    int getDefense();

    void setDefense(int defense);

    void addDefense(int defense);

    int getDodge();

    void setDodge(int dodge);

    void addDodge(int dodge);

    int getMana();

    void setMana(int mana);

    void addMana(int mana);

    int getMagic_damage();

    void setMagic_damage(int magic_damage);

    void addMagic_damage(int magic_damage);

    int getAttack_damage();

    void setAttack_damage(int attack_damage);

    void addAttack_damage(int attack_damage);

    int getXp();

    void addXp(int xp);

    void setXp(int xp);

    int getLevel();

    void setLevel(int level);

    void addLevel(int level);
}
