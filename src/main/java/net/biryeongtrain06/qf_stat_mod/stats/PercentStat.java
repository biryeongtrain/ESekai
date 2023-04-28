package net.biryeongtrain06.qf_stat_mod.stats;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.OptionalDouble;
import java.util.function.BiFunction;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.PERCENT;

public class PercentStat implements IStats {
    private final Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> map = new Object2ObjectOpenHashMap<>();
    private final StatTypes type;

    public PercentStat(StatTypes type, float basePercent) {
        this(type);
        map.get(PERCENT).put(getBaseStatId(), basePercent);
    }

    public PercentStat(StatTypes type) {
        this.type = type;
        map.put(PERCENT, new Object2FloatOpenHashMap<>());
    }

    @Override
    public void addStatInstance(Identifier id, float value, StatSubTag tag) {
        if (tag != PERCENT) return;

        var instance = map.get(PERCENT);
        instance.put(id, value);
    }

    @Override
    public float getTotalValue() {
        OptionalDouble option = map.get(PERCENT).values().doubleStream().reduce(Double::sum);
        return (float) option.orElse(0);
    }

    @Override
    public boolean tryReplaceInstance(Identifier id, float value, StatSubTag tag) {
        if (tag != PERCENT) return false;
        if (!hasInstance(id, tag)) return false;

        var instance = map.get(tag);
        instance.replace(id, value);
        return true;
    }

    @Override
    public boolean removeStatInstance(Identifier id, StatSubTag tag) {
        if(!hasInstance(id, tag)) return false;

        var instance = map.get(tag);
        instance.removeFloat(id);
        return true;
    }

    @Override
    public boolean hasInstance(Identifier id, StatSubTag tag) {
        return map.get(tag).containsKey(id);
    }

    @Override
    public float getInstanceValueById(Identifier id, StatSubTag tag) {
        if (tag == PERCENT) return 0;
        var instanceMap = map.get(tag);

        if (instanceMap.containsKey(id)) return instanceMap.getFloat(id);

        return 0;
    }

    @Override
    public Object2FloatOpenHashMap<Identifier> getCloneInstances(StatSubTag tag) {
        if (tag != PERCENT) return null;
        return map.get(tag).clone();
    }

    @Override
    public Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> getCloneMap() {
        return this.map.clone();
    }

    @Override
    public NbtList getSeparatedStatLore() {
        return null;
    }

    @Override
    public NbtList getCombinedStatLore() {
        return null;
    }

    @Override
    public float getTagValue(StatSubTag tag) {
        return getTotalValue();
    }

    @Override
    public void mergeInstance(Identifier id, StatSubTag tag, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
        var instance = map.get(tag);
        instance.merge(id, value, remappingFunction);
    }
}
