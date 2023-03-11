package net.biryeongtrain06.qf_stat_mod.entity.modifier.stat.defensive;

import net.biryeongtrain06.qf_stat_mod.entity.modifier.ModifierSubType;
import net.biryeongtrain06.qf_stat_mod.entity.modifier.stat.AbstractStatModifier;
import net.biryeongtrain06.qf_stat_mod.entity.modifier.stat.ModifierTier;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.util.Identifier;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.FLAT;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.ARMOR;

public class Tier1ArmorModifier extends AbstractStatModifier {

    public Tier1ArmorModifier(int level) {
        super(level);
    }

    @Override
    protected Identifier getStatId() {
        return TextHelper.getId("tier1_armor_flat_modifier");
    }

    @Override
    protected EnumMap<StatTypes, IStats> getMap() {
        EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);
        IStats armorStat = new FloatStat();

        armorStat.addStatInstance(getStatId(), 30, FLAT);
        map.put(ARMOR, armorStat);

        return map;
    }

    @Override
    protected String getModifierName() {
        return "Extra Armor";
    }

    @Override
    protected ModifierSubType getSubType() {
        return ModifierSubType.DEFENSIVE;
    }

    @Override
    protected ModifierTier getTier() {
        return ModifierTier.TIER_1;
    }
}
