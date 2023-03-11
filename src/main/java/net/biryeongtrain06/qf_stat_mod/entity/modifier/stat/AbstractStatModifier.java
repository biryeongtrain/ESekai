package net.biryeongtrain06.qf_stat_mod.entity.modifier.stat;

import net.biryeongtrain06.qf_stat_mod.entity.modifier.Modifier;
import net.biryeongtrain06.qf_stat_mod.entity.modifier.ModifierSubType;
import net.biryeongtrain06.qf_stat_mod.entity.modifier.ModifierType;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.util.Identifier;

import java.util.EnumMap;

public abstract class AbstractStatModifier implements Modifier {
    final int level;
    protected abstract Identifier getStatId();
    protected abstract EnumMap<StatTypes, IStats> getMap();
    protected abstract String getModifierName();
    protected abstract ModifierSubType getSubType();
    protected abstract ModifierTier getTier();

    public AbstractStatModifier(int level) {
        this.level = level;
    }

    @Override
    public String getName() {
        return getModifierName();
    }

    @Override
    public ModifierType getModifierType() {
        return ModifierType.STAT;
    }

    @Override
    public ModifierSubType getModifierSubType() {
        return getSubType();
    }

    @Override
    public EnumMap<StatTypes, IStats> getStatsMap() {
        return getMap();
    }

    @Override
    public ModifierTier getModifierTier() {
        return getTier();
    }
}
