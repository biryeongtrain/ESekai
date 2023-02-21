package net.biryeongtrain06.qf_stat_mod.stats;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.*;

public class HealthFlat implements IStats {
    Object2FloatOpenHashMap<Identifier> map = new Object2FloatOpenHashMap<>();

    public HealthFlat() {

    }

    @Override
    public void addStatInstance(Identifier id, float value) {
        map.put(id, value);
    }

    @Override
    public float getTotalValue() {
        Optional<Float> optional = map.values().stream().reduce(Float::sum);
        if (optional.isEmpty()) return 1f;
        if (optional.get() <= 0) return 1f;
        return optional.get();
    }

    @Override
    public boolean tryModifyInstance(Identifier id, float value) {
        if (!hasInstance(id)) return false;
        map.replace(id, value);
        return true;
    }

    @Override
    public boolean removeStatInstance(Identifier id) {
        if (!hasInstance(id)) return false;
        map.removeFloat(id);
        return true;
    }

    @Override
    public boolean hasInstance(Identifier id) {
        return map.containsKey(id);
    }
}
