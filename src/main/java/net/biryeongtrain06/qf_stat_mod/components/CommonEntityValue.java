package net.biryeongtrain06.qf_stat_mod.components;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.entity.EntityRank;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemGameRules;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;


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
        return this.rank.equals(EntityRank.UN_DECIDED) && !provider.hasCustomName() && provider instanceof HostileEntity;
    }

    public CommonEntityValue(MobEntity provider) {
        this.provider = provider;
        if (canApplyModifier(this.provider)) {
            setRankRandomly();
        }
        setLevel();
    }

    @Override
    public void setRankRandomly() {
        if (!(this.rank.equals(EntityRank.UN_DECIDED))) {
            return;
        }
        float value = (float) (Math.random() * 100);

        for(EntityRank rank : EntityRank.values()) {
            if (rank == EntityRank.UN_DECIDED) {
                continue;
            }
            if (value < rank.getSpawn_chance()) {
                value -= rank.getSpawn_chance();
            } else {
                this.rank = rank;
                return;
            }
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
    public void tryHealthIncrease() {
        if (healthIncreased) {
            return;
        }

    }

    @Override
    public void setLevel() {
        final int MAX_LEVEL = ExpHandler.getMaxLevel();
        if (provider instanceof EnderDragonEntity) {
            this.level = (int) (MAX_LEVEL / 0.8);
        }
        if (provider instanceof WitherEntity) {
            this.level =  (int) (MAX_LEVEL / 0.6);
        }
        if (provider.world.isClient) {
            return;
        }

        final int SCALING_DISTANCE = ExpHandler.getScalingDistance();
        boolean gameRule = provider.getWorld().getGameRules().getBoolean(QfStatSystemGameRules.ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING);
        final ServerPlayerEntity nearestPlayer = PlayerHelper.getNearestPlayer((ServerWorld) provider.world, provider);

        if (gameRule || nearestPlayer != null) {
            final int nearestPlayerLevel = DataStorage.loadPlayerStat(nearestPlayer).getLevel();
            final int MIN = MathHelper.clamp(nearestPlayerLevel - 5, 0, MAX_LEVEL);
            final int MAX = MathHelper.clamp(nearestPlayerLevel + 5, 0, MAX_LEVEL);
            this.level = (int) Math.random() * MAX + MIN;
            return;
        }
        BlockPos spawnPos = provider.getWorld().getSpawnPos();
        double distance = spawnPos.getManhattanDistance(provider.getBlockPos());
        this.level = (int) (distance % SCALING_DISTANCE) + 1;
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
