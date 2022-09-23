package net.biryeongtrain06.qf_stat_mod.component;

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

        int getEarthResistance();

        void addEarthResistance(int value);

        void setEarthResistance(int value);

        int getLightResistance();

        void addLightResistance(int value);

        void setLightResistance(int value);

        int getDarkResistance();

        void addDarkResistance(int value);

        void setDarkResistance(int value);

        int getReducePhysicalDMG();
        int addReducePhysicalDMG(int value);
        int setReducePhysicalDMG(int value);
}
