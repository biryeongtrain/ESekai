package net.biryeongtrain06.qf_stat_mod.stats.interfaces;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.util.Identifier;

public interface IStats {
    Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> getInstance();
    void addStatInstance(Identifier id, float value, StatSubTag tag);
    float getTotalValue();
    boolean tryReplaceInstance(Identifier id, float value, StatSubTag tag);
    boolean removeStatInstance(Identifier id, StatSubTag tag);
    boolean hasInstance(Identifier id, StatSubTag tag);
}
