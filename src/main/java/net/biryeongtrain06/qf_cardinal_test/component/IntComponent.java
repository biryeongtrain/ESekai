package net.biryeongtrain06.qf_cardinal_test.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

public interface IntComponent extends ComponentV3 {
    int getValue();

}

class RandomIntComponent implements IntComponent {

    private int value = (int) (Math.random() *20);

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.value = tag.getInt("value");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("value", this.value);
    }
}

class SyncedIntComponent implements IntComponent, AutoSyncedComponent {

    private int value;
    private final Entity provider;

    public SyncedIntComponent(Entity provider) {
        this.provider = provider;
    }

    public void setValue(int value) {
        this.value = value;
        MyComponents.MAGIK.sync(this.provider);
    }
    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.value = tag.getInt("value");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("value", this.value);
    }
}

class IncrementingIntComponent implements IntComponent, ServerTickingComponent {

    private int value;
    @Override
    public void serverTick() {
        this.value++;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.value = tag.getInt("value");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("value", this.value);
    }
}