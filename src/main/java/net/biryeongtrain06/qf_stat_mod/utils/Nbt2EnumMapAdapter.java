package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.EnumMap;

public class Nbt2EnumMapAdapter {
    public static NbtCompound ConvertMapAsNbtCompound(EnumMap<StatTypes, IStats> instance) {
        NbtCompound nbtCompound = new NbtCompound();
        instance.forEach(((statTypes, iStats) -> {
            // HEALTH, ARMOR etc...
            String typeName = statTypes.getName();
            NbtCompound nbtCompound1 = new NbtCompound();
            iStats.getCloneMap().forEach((tag, map) -> {
                // FLAT, PERCENT, MULTI
                NbtCompound nbtCompound2 = new NbtCompound();
                map.forEach((id, value) -> {
                    // id, value
                    nbtCompound2.putFloat(id.toString(), value);
                });
                nbtCompound1.put(tag.name(), nbtCompound2);
            });
            nbtCompound.put(typeName ,nbtCompound1);
        }));
        return nbtCompound;
    }

    public static EnumMap<StatTypes, IStats> ConvertNbtCompoundAsMap(NbtCompound nbtCompound) {
        EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);
        nbtCompound.getKeys().forEach(stat -> { // Root

            StatTypes type = StatTypes.getStatByName(stat);
            IStats statClazz;
            NbtCompound nbtCompound2 = nbtCompound.getCompound(stat); // FLAT, PERCENT, MULTI
            if (nbtCompound2.getSize() == 3) statClazz = new FloatStat(type);
            else statClazz = new PercentStat(type);

            nbtCompound2.getKeys().forEach(tag -> {
                NbtCompound nbtCompound3 = nbtCompound2.getCompound(tag); // ID , value

                nbtCompound3.getKeys().forEach(id -> statClazz.addStatInstance(new Identifier(id), nbtCompound3.getFloat(id), StatSubTag.getStatByName(tag)));
            });
            map.put(type, statClazz);
        });
        return map;
    }
}
