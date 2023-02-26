package net.biryeongtrain06.qf_stat_mod.utils.builder;

import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.minecraft.entity.Entity;

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
        return null;
    }

    @Override
    public void setArmor(float flat, float percent, float multi) {
        map.put()
    }

    @Override
    public AbstractCommonStatBuilder setDodge(float percent) {

        return null;
    }

    @Override
    public AbstractCommonStatBuilder setFireRes(float percent) {

    }

    @Override
    public AbstractCommonStatBuilder setWaterRes(float percent) {

    }

    @Override
    public AbstractCommonStatBuilder setEarthRes(float percent) {

    }

    @Override
    public AbstractCommonStatBuilder setLightRes(float percent) {

    }

    @Override
    public AbstractCommonStatBuilder setDarkRes(float percent) {

    }

    @Override
    public AbstractCommonStatBuilder setHealEfficient(float flat, float percent, float multi) {

    }

    @Override
    public AbstractCommonStatBuilder setMeleeDamageBonus(float flat, float percent, float multi) {

    }

    @Override
    public AbstractCommonStatBuilder setRangedDamageBonus(float flat, float percent, float multi) {

    }

    @Override
    public AbstractCommonStatBuilder setBonusXp(float percent) {

        return null;
    }

    @Override
    public AbstractCommonStatBuilder build() {
        return null;
    }
}
