package net.biryeongtrain06.qf_stat_mod.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;

import java.util.HashMap;

@SuppressWarnings("unused")
public interface ICommonEntityComponents extends Component, ServerTickingComponent {
    int getLevel();
    void setLevel(int val);
    void addLevel(int val);

    int getDefense();
    void setDefense(int val);
    void addDefense(int val);

    int getDodge();
    void setDodge(int val);
    void addDodge(int val);
    int getAdditionalDefenseRate();
    void setAdditionalDefenseRate(int val);
    void addAdditionalDefenseRate(int val);
    int getFireResistance();
    void setFireResistance(int val);
    int getWaterResistance();
    void setWaterResistance(int val);
    int getEarthResistance();
    void setEarthResistance(int val);
    int getLightResistance();
    void setLightResistance(int val);
    int getDarkResistance();
    void setDarkResistance(int val);

    void setRankRandomly();
    void setModifierRandomly();
    void tryHealthIncrease();
    void tryDamageIncrease();
    void setLevel();
    void initElement();
    HashMap<StatTypes, Integer> getDefensiveMap();
}
