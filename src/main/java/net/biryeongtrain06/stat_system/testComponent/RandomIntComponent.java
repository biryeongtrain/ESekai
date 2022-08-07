package net.biryeongtrain06.stat_system.testComponent;

import net.minecraft.nbt.NbtCompound;

public class RandomIntComponent implements IntComponent {
    private int value = (int) (Math.random() * 50); // random initial value because why not

    @Override
    public int getValue() {
        return this.value;
    }
    public void setValue(int v){
        this.value = v;
    }
    @Override
    public void increment() {
        int newRandom =(int) (Math.random() * 10);
        this.value += newRandom ;
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