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

        float getAttack_damage();

        void setAttack_damage(float attack_damage);

        void addAttack_damage(float attack_damage);

        int getLevel();

        void setLevel(int level);

        int getFireResistance();

        void addFireResistance(int value);

        void setFireResistance(int value);

        int  getWaterResistance();

        void addWaterResistance(int value);

        void setWaterResistance(int value);

        int getNatureResistance();

        void addNatureResistance(int value);

        void setNatureResistance(int value);

        int getLightResistance();

        void addLightResistance(int value);

        void setLightResistance(int value);

        int getDarkResistance();

        void addDarkResistance(int value);

        void setDarkResistance(int value);
}
