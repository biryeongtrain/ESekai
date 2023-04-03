package net.biryeongtrain06.qf_stat_mod.stats.interfaces;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public interface IStats {
    default Identifier getBaseStatId() {
        return TextHelper.getId("base_value");
    }

    void addStatInstance(Identifier id, float value, StatSubTag tag);
    float getTotalValue();
    boolean tryReplaceInstance(Identifier id, float value, StatSubTag tag);
    boolean removeStatInstance(Identifier id, StatSubTag tag);
    boolean hasInstance(Identifier id, StatSubTag tag);
    float getInstanceValueById(Identifier id, StatSubTag tag);
    void mergeInstance(Identifier id, StatSubTag tag, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction);
    Object2FloatOpenHashMap<Identifier> getCloneInstances(StatSubTag tag);
    Object2ObjectOpenHashMap<StatSubTag, Object2FloatOpenHashMap<Identifier>> getCloneMap();
    NbtList getSeparatedStatLore();
    NbtList getCombinedStatLore();
}
