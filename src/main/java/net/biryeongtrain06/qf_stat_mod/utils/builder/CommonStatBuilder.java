package net.biryeongtrain06.qf_stat_mod.utils.builder;

import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.StatType;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.*;

public class CommonStatBuilder extends AbstractCommonStatBuilder{
    public CommonStatBuilder(Entity entity) {
        super(entity);
    }

    @Override
    public AbstractCommonStatBuilder setHealth(float flat, float percent, float multi) {
        map.put(HEALTH, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setHealthRegen(float flat, float percent, float multi) {
        map.put(REGEN_HEALTH_PER_SECOND, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setMana(float flat, float percent, float multi) {
        map.put(MANA, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setManaRegen(float flat, float percent, float multi) {
        map.put(REGEN_MANA_PER_SECOND, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setArmor(float flat, float percent, float multi) {
        map.put(ARMOR, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setDodge(float percent) {
        map.put(DODGE, new PercentStat(percent));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setFireRes(float percent) {
        map.put(FIRE_RESISTANCE, new PercentStat(percent));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setWaterRes(float percent) {
        map.put(WATER_RESISTANCE, new PercentStat(percent));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setEarthRes(float percent) {
        map.put(EARTH_RESISTANCE, new PercentStat(percent));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setLightRes(float percent) {
        map.put(LIGHT_RESISTANCE, new PercentStat(percent));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setDarkRes(float percent) {
        map.put(DARK_RESISTANCE, new PercentStat(percent));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setHealEfficient(float flat, float percent, float multi) {
        map.put(HEAL_EFFICIENT, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setMeleeDamageBonus(float flat, float percent, float multi) {
        map.put(BONUS_MELEE_DAMAGE, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setRangedDamageBonus(float flat, float percent, float multi) {
        map.put(BONUS_RANGED_DAMAGE, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setBonusXp(float percent) {
        map.put(BONUS_XP, new PercentStat(percent));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setStrength(float flat, float percent, float multi) {
        map.put(STRENGTH, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setDexterity(float flat, float percent, float multi) {
        map.put(DEXTERITY, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setConstitution(float flat, float percent, float multi) {
        map.put(CONSTITUTION, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setIntelligence(float flat, float percent, float multi) {
        map.put(INTELLIGENCE, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setWisdom(float flat, float percent, float multi) {
        map.put(WISDOM, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder setCharisma(float flat, float percent, float multi) {
        map.put(CHARISMA, new FloatStat(flat, percent, multi));
        return this;
    }

    @Override
    public AbstractCommonStatBuilder build() {
        if (entity instanceof PlayerEntity) {
            return buildPlayerStat();
        }
        return buildEntityStat();
    }

    private AbstractCommonStatBuilder buildPlayerStat() {
        StatTypes.getAvailablePlayerStats().forEach(stat -> {
            if(hasStat(stat)) {
                return;
            }
            if(stat.isOnlyPercent) {
                map.put(stat, new PercentStat(0));
                return;
            }
            map.put(stat, new FloatStat(0, 0, 0));
        });
        return this;
    }

    private AbstractCommonStatBuilder buildEntityStat() {

        return this;
    }
    private boolean hasStat(StatTypes stat) {
        return map.containsKey(stat);
    }
}
