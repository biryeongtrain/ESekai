package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface EntityStatComponentInterface extends Component {

        int getHealth();

        void setHealth(int health);

        void addHealth(int health);

        int getDefense();

        void setDefense(int defense);

        void addDefense(int defense);

        int getDodge();

        void setDodge(int dodge);

        void addDodge(int dodge);

        int getAttack_damage();

        void setAttack_damage(int attack_damage);

        void addAttack_damage(int attack_damage);

        int getLevel();

        void setLevel(int level);
}
