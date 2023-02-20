package net.biryeongtrain06.qf_stat_mod.stats.interfaces;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.util.Identifier;

public interface IStats {
    void addStatInstance(Identifier id, float value);
    float getTotalValue();
    boolean removeStatInstance(Identifier id);
    boolean hasInstance(Identifier id);
}
