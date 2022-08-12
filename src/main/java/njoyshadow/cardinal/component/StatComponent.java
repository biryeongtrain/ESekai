package njoyshadow.cardinal.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface StatComponent extends Component {

    int getHealth();

    void setHealth(int health);

    int getDefense();

    void setDefense(int defense);

    int getDodge();

    void setDodge(int dodge);

    int getMana();

    void setMana(int mana);

    int getMagic_damage();

    void setMagic_damage(int magic_damage);

    int getAttack_damage();

    void setAttack_damage();
}