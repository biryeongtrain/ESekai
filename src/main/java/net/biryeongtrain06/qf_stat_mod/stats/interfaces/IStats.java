package net.biryeongtrain06.qf_stat_mod.stats.interfaces;

import net.minecraft.util.Identifier;

public interface IStats {
    void addStatInstance(Identifier id, float value);
    float getTotalValue();
    boolean tryModifyInstance(Identifier id, float value);
    boolean removeStatInstance(Identifier id);
    boolean hasInstance(Identifier id);
}
