package net.biryeongtrain06.qf_stat_mod.utils.builder;

public abstract class AbstractCommonStatBuilder {

    public abstract void setHealth(float flat, float percent, float multi);
    public abstract void setHealthRegen(float flat, float percent, float multi);
    public abstract void setMana(float flat, float percent, float multi);
    public abstract void setArmor(float flat, float percent, float multi);
    public abstract void setDodge(float percent);
    public abstract void setFireRes(float percent);
    public abstract void setWaterRes(float percent);
    public abstract void setEarthRes(float percent);
    public abstract void setLightRes(float percent);
    public abstract void setDarkRes(float percent);
    public abstract void setHealEfficient(float flat, float percent, float multi);
    public abstract void setMeleeDamageBonus(float flat, float percent, float multi);
    public abstract void setRangedDamageBonus(float flat, float percent, float multi);
    public abstract void setBonusXp(float percent);
}
