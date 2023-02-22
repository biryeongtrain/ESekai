package net.biryeongtrain06.qf_stat_mod.stats;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.*;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.MULTIPLIER;

public class NumberStat implements IStats {
    Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> map = new Object2ObjectOpenHashMap<>();

    public NumberStat() {
        map.put(FLAT, new Object2FloatOpenHashMap<>());
        map.put(PERCENT, new Object2FloatOpenHashMap<>());
        map.put(MULTIPLIER, new Object2FloatOpenHashMap<>());
    }

    @Override
    public Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> getInstance() {
        return map;
    }

    public void addStatInstance(Identifier id, float value, StatSubTag tag) {
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        instance.put(id, value);
    }

    public float getTotalValue() {
        return getTagValue(FLAT) * getTagValue(PERCENT) * getTagValue(MULTIPLIER);
    }

    public float getTagValue(StatSubTag tag) {
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        Optional<Float> optional = instance.values().stream().reduce(Float::sum);
        if (optional.isEmpty()) return 1f;
        if (optional.get() <= 0) return 1f;
        return optional.get();
    }

    public boolean tryReplaceInstance(Identifier id, float value, StatSubTag tag) {
        if (!hasInstance(id, tag)) return false;
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        instance.replace(id, value);
        return true;
    }

    public boolean hasInstance(Identifier id, StatSubTag tag) {
        return map.get(tag).containsKey(id);
    }

    public boolean removeStatInstance(Identifier id, StatSubTag tag) {
        if (!hasInstance(id, tag)) return false;
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        instance.removeFloat(id);
        return true;
    }
}
