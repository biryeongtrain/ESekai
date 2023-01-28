package net.biryeongtrain06.qf_stat_mod.components;

import net.biryeongtrain06.qf_stat_mod.entity.EntityRank;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;


public class CommonEntityValue implements ICommonEntityComponents {
    private int level = 1;
    private int defense = 0;
    private int additionalDefenseRate = 0;
    private int dodge = 0;
    private EntityRank rank =  EntityRank.UN_DECIDED;
    private final MobEntity provider;
    private int numMaxAbilities = EntityRank.UN_DECIDED.getAbilities();
    private boolean healthIncreased = false;

    public boolean canApplyModifier(MobEntity provider) {
        return this.rank.equals(EntityRank.UN_DECIDED) && !provider.hasCustomName();
    }

    public CommonEntityValue(MobEntity provider) {
        this.provider = provider;
        if (canApplyModifier(this.provider)) {
            setRankRandomly();
        }
    }

    @Override
    public void setRankRandomly() {
        if (!(this.rank.equals(EntityRank.UN_DECIDED))) {
            return;
        }
        float value = (float) (Math.random() * 100);

        for (EntityRank rank : EntityRank.values()) {
            if (value < rank.getSpawn_chance()) {
                this.rank = rank;
                break;
            }
            value -= rank.getSpawn_chance();
        }
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int val) {
        this.level = val;
    }

    @Override
    public void addLevel(int val) {
        this.level += val;
    }

    @Override
    public int getDefense() {
        return this.defense;
    }

    @Override
    public void setDefense(int val) {
        this.defense = val;
    }

    @Override
    public void addDefense(int val) {
        this.defense += val;
    }

    @Override
    public int getAdditionalDefenseRate() {
        return this.additionalDefenseRate;
    }

    @Override
    public void setAdditionalDefenseRate(int val) {
        this.additionalDefenseRate = val;
    }

    @Override
    public void addAdditionalDefenseRate(int val) {
        this.additionalDefenseRate += val;
    }

    @Override
    public void setModifierRandomly() {
        if (rank != EntityRank.COMMON && rank != EntityRank.UN_DECIDED) {

        }
    }

    @Override
    public void serverTick() {

    }

    @Override
    public void readFromNbt(NbtCompound tag) {

    }

    @Override
    public void writeToNbt(NbtCompound tag) {

    }
}
