package njoyshadow.cardinal.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface IntComponent extends Component {
    int getValue();
    void increment();
    void setValue(int value);
}
