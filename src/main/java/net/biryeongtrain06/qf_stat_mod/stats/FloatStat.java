package net.biryeongtrain06.qf_stat_mod.stats;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.*;

public class FloatStat implements IStats {
    private final Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> map = new Object2ObjectOpenHashMap<>();

    public FloatStat() {
        map.put(FLAT, new Object2FloatOpenHashMap<>());
        map.put(PERCENT, new Object2FloatOpenHashMap<>());
        map.put(MULTIPLIER, new Object2FloatOpenHashMap<>());
    }

    public FloatStat(float baseFlat, float basePercent, float baseMulti) {
        this();
        map.get(FLAT).put(getBaseStatId(), baseFlat);
        map.get(PERCENT).put(getBaseStatId(), basePercent);
        map.get(MULTIPLIER).put(getBaseStatId(), baseMulti);
    }

    @Override
    public void addStatInstance(Identifier id, float value, StatSubTag tag) {
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        instance.put(id, value);
    }

    @Override
    public float getTotalValue() {
        return getTagValue(FLAT) * getTagValue(PERCENT) * getTagValue(MULTIPLIER);
    }

    public float getTagValue(StatSubTag tag) {
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        Optional<Float> optional = instance.values().stream().reduce(Float::sum);
        if (optional.isEmpty()) return 1f;
        return optional.get();
    }

    @Override
    public boolean tryReplaceInstance(Identifier id, float value, StatSubTag tag) {
        if (!hasInstance(id, tag)) return false;
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        instance.replace(id, value);
        return true;
    }

    @Override
    public boolean hasInstance(Identifier id, StatSubTag tag) {
        return map.get(tag).containsKey(id);
    }

    @Override
    public float getInstanceValueById(Identifier id, StatSubTag tag) {
        var instanceMap = map.get(tag);
        if (instanceMap.containsKey(id)) return instanceMap.getFloat(id);
        return 0;
    }


    @Override
    public Object2FloatOpenHashMap<Identifier> getCloneInstances(StatSubTag tag) {
        return map.get(tag).clone();
    }

    @Override
    public Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> getCloneMap() {
        return this.map.clone();
    }

    @Override
    public boolean removeStatInstance(Identifier id, StatSubTag tag) {
        if (!hasInstance(id, tag)) return false;
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        instance.removeFloat(id);
        return true;
    }
}
