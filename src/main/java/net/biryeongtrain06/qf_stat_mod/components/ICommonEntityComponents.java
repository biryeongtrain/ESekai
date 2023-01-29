package net.biryeongtrain06.qf_stat_mod.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

public interface ICommonEntityComponents extends Component, ServerTickingComponent {
    int getLevel();
    void setLevel(int val);
    void addLevel(int val);

    int getDefense();
    void setDefense(int val);
    void addDefense(int val);

    int getAdditionalDefenseRate();
    void setAdditionalDefenseRate(int val);
    void addAdditionalDefenseRate(int val);

    void setRankRandomly();
    void setModifierRandomly();
    void tryHealthIncrease();

}
