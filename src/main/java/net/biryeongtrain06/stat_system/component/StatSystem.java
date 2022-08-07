package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface StatSystem extends Component {

    int getValue();
    void addValue();
    void setValue();
    void levelUp();

}
