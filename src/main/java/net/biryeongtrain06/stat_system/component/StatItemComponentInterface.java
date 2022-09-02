package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface StatItemComponentInterface extends Component, PlayerStatComponentInterface {
    String getElement();
    void setElement();
    int getEquipmentLevel();
    void setEquipmentLevel();


    //TODO Make Set Method
}
