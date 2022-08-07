package net.biryeongtrain06.stat_system.component;

import net.biryeongtrain06.stat_system.MainStatSystem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class StatEntry {

    public final Identifier id;
    private float value;

    public StatEntry(Identifier identifier, int value) {
        this.id = identifier;
        this.value = value;
    }

    public float getValue() { return value; }

    public void setXp(int xp) {
        MainStatSystem.debugLogger.info("Now Stat " + id.toString() + "value is" + xp);
        this.value = value;
    }

    public void addXp(int xp) {
        MainStatSystem.debugLogger.info("Now Stat " + id.toString() + "value is " + xp);
        this.value += xp;
    }
}
