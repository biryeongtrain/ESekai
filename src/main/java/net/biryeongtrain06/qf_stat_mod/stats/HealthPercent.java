package net.biryeongtrain06.qf_stat_mod.stats;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class HealthPercent implements IStats {
    Object2FloatMap<Identifier> map;

    @Override
    public void addStatInstance(Identifier id, float value) {
        map.put(id, value);
    }

    @Override
    public float getTotalValue() {
        Optional<Float> optional = map.values().stream().reduce(Float::sum);
        return optional.orElse(0f);
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
