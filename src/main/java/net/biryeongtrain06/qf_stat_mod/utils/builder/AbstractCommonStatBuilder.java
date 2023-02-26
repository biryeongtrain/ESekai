package net.biryeongtrain06.qf_stat_mod.utils.builder;

import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.Entity;

import java.util.EnumMap;

public abstract class AbstractCommonStatBuilder {
    protected final EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);
    protected final Entity entity;

    public AbstractCommonStatBuilder(Entity entity) {
        this.entity = entity;
    }

    public abstract AbstractCommonStatBuilder setHealth(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setHealthRegen(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setMana(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setManaRegen(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setArmor(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setDodge(float percent);
    public abstract AbstractCommonStatBuilder setFireRes(float percent);
    public abstract AbstractCommonStatBuilder setWaterRes(float percent);
    public abstract AbstractCommonStatBuilder setEarthRes(float percent);
    public abstract AbstractCommonStatBuilder setLightRes(float percent);
    public abstract AbstractCommonStatBuilder setDarkRes(float percent);
    public abstract AbstractCommonStatBuilder setHealEfficient(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setMeleeDamageBonus(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setRangedDamageBonus(float flat, float percent, float multi);
    public abstract AbstractCommonStatBuilder setBonusXp(float percent);
    public abstract AbstractCommonStatBuilder build();
}
