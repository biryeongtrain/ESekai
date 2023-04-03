package net.biryeongtrain06.qf_stat_mod.stats;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.BiFunction;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.*;

public class FloatStat implements IStats {
    private final Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> map = new Object2ObjectOpenHashMap<>();
    private final StatTypes type;
    public FloatStat(StatTypes type) {
        this.type = type;
        map.put(FLAT, new Object2FloatOpenHashMap<>());
        map.put(PERCENT, new Object2FloatOpenHashMap<>());
        map.put(MULTIPLIER, new Object2FloatOpenHashMap<>());
    }

    public FloatStat(StatTypes type, float baseFlat, float basePercent, float baseMulti) {
        this(type);
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
        return optional.orElse(1f);
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
    public void mergeInstance(Identifier id, StatSubTag tag, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
        var instance = map.get(tag);
        instance.merge(id, value, remappingFunction);
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
    public NbtList getSeparatedStatLore() {
        NbtList nbtList = new NbtList();
        this.map.forEach((tag, tagInstance) ->
                tagInstance.keySet().forEach(key -> {
                float value = tagInstance.getFloat(key);
                nbtList.add(NbtString.of(Text.Serializer.toJson(Text.translatable(TextHelper.createTranslation(tag.toString().toLowerCase() + "_tooltip"), type.getTranslatableName(), value).formatted(type.getFormat()))));
            })
        );
        return nbtList;
    }

    @Override
    public NbtList getCombinedStatLore() {
        NbtList nbtList = new NbtList();
        float value = getTotalValue();

        nbtList.add(NbtString.of(Text.Serializer.toJson(Text.translatable(TextHelper.createTranslation(FLAT.toString().toLowerCase() + "_tooltip"), type.getTranslatableName(), value).formatted(type.getFormat()))));
        return nbtList;
    }

    @Override
    public boolean removeStatInstance(Identifier id, StatSubTag tag) {
        if (!hasInstance(id, tag)) return false;
        Object2FloatOpenHashMap<Identifier> instance = map.get(tag);
        instance.removeFloat(id);
        return true;
    }
}
