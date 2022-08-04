package njoyshadow.cardinal.component;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ItemIntComponent extends ItemComponent implements IntComponent {
    private int value;
    public ItemIntComponent(ItemStack stack) {
        super(stack);
        this.value =  (int) (Math.random() * 50);
    }

    @Override
    public int getValue() {
        if (!this.hasTag("value", NbtType.INT)) this.putInt("value", 10);
        return this.getInt("value");
    }

    @Override
    public void increment() {
        this.putInt("value", this.getValue() + this.value); // performs initialization through getValue()
    }

    @Override
    public void setValue(int v) {
        this.putInt("value", v);
    }
}
