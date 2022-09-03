package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface NonMagicWeaponComponentInterface extends Component {

    int getAttack_damage();
    void addAttack_damage(int attack_damage);
    void setAttack_damage(int attack_damage);

}
