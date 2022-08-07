package net.biryeongtrain06.stat_system.component;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class EntityStatSystem implements StatSystem{

    private int level = 0;
    private Map<String, Integer> stat = new HashMap<>();

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void addValue() {

    }

    @Override
    public void setValue() {

    }

    @Override
    public void levelUp() {

    }

    @Override
    public void readFromNbt(NbtCompound tag) {

    }

    @Override
    public void writeToNbt(NbtCompound tag) {

    }

}

