package net.biryeongtrain06.qf_stat_mod.components;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemGameRules;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.EntityRank;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.Elements.*;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.EntityRank.getRankById;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums.*;

/**
 * 엔티티에게만 적용되는 Cardinal Components용 Class 입니다.
 */
public class CommonEntityValue implements ICommonEntityComponents {
    private int level = 1;
    private Elements attackElement = PHYSICAL;
    private int additionalDefenseRate = 0;
    private int damage = 0;
    private HashMap<StatEnums, Integer> defensiveMap = new HashMap<>();
    private EntityRank rank =  EntityRank.UN_DECIDED;
    private final MobEntity provider;
    private int numMaxAbilities = EntityRank.UN_DECIDED.getAbilities();
    private boolean healthIncreased = false;

    public boolean canApplyModifier(MobEntity provider) {
        return this.rank.equals(EntityRank.UN_DECIDED) && !provider.hasCustomName() && provider instanceof HostileEntity;
    }


    public CommonEntityValue(MobEntity provider) {
        this.provider = provider;
        initDefensiveMap();
        if (canApplyModifier(this.provider)) {
            setRankRandomly();
        }
        else this.rank = EntityRank.COMMON;
        setLevel();
        provider.getServer().sendMessage(Text.literal("Entity Spawned, Level : " + level + ", Rank : " + this.rank.getName() + "Elements : " + this.attackElement.name()));
        getDefensiveMap().forEach(((statEnums, integer) -> provider.getServer().sendMessage(Text.literal(statEnums.getName() + " : " + integer))));
    }

    /**
     * 엔티티의 기본적인 등급을 정합니다. 등급은 데이터팩의 rarity.json 을 토대로 작성됩니다.
     * @see EntityRank
     */
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
            if (value < rank.getSpawnChance()) {
                value -= rank.getSpawnChance();
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
    public int getFireResistance() {
        return getDefensiveMap().get(FIRE_RESISTANCE);
    }

    @Override
    public void setFireResistance(int val) {
        this.defensiveMap.replace(FIRE_RESISTANCE, val);
    }

    @Override
    public int getWaterResistance() {
        return getDefensiveMap().get(WATER_RESISTANCE);
    }

    @Override
    public void setWaterResistance(int val) {
        getDefensiveMap().replace(WATER_RESISTANCE, val);
    }

    @Override
    public int getEarthResistance() {
        return getDefensiveMap().get(EARTH_RESISTANCE);
    }

    @Override
    public void setEarthResistance(int val) {
        getDefensiveMap().replace(EARTH_RESISTANCE, val);
    }

    @Override
    public int getLightResistance() {
        return getDefensiveMap().get(LIGHT_RESISTANCE);
    }

    @Override
    public void setLightResistance(int val) {
        getDefensiveMap().replace(LIGHT_RESISTANCE, val);
    }

    @Override
    public int getDarkResistance() {
        return getDefensiveMap().get(DARK_RESISTANCE);
    }

    @Override
    public void setDarkResistance(int val) {
        getDefensiveMap().replace(DARK_RESISTANCE, val);
    }

    @Override
    public void setModifierRandomly() {
        if (rank != EntityRank.COMMON && rank != EntityRank.UN_DECIDED) {

        }
    }

    /**
     * 엔티티의 체력을 변경합니다. 이미 변경이 완료된 엔티티는 실행되지 않습니다.
     * @see EntityRank EntityRank에서 엔티티의 체력 배수를 볼 수 있습니다.
     */
    @Override
    public void tryHealthIncrease() {
        if (healthIncreased) {
            return;
        }
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        int randomValue = (int) ((Math.random() * 0.5) + 0.5);
        int value = (int) (provider.getMaxHealth() * (this.level + randomValue + this.rank.getStatMultiplier()));
        if (entityAttributeInstance.hasModifier(getModifier(rank, value))) entityAttributeInstance.addPersistentModifier(getModifier(rank, value));
        provider.setHealth((float) provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
        this.healthIncreased = true;
    }

    /**
     * @param rank
     * @param value
     * @return rank 에 따른 이름과 value에 따른 수치를 가진 Attribute를 반환합니다.
     */
    public EntityAttributeModifier getModifier(EntityRank rank, int value) {
        return new EntityAttributeModifier(rank.name() + "_HEALTH_BOOST", value, EntityAttributeModifier.Operation.ADDITION);
    }

    /**
     * 엔티티의 레벨을 설정합니다. 엔티티의 레벨은 게임 룰에 따라서 변경됩니다.
     * true 일 경우 근처 플레이어의 레벨을 따르고, false 일 경우 스폰 지역에서의 거리를 따릅니다.
     * @see QfStatSystemGameRules
     */
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

    /**
     * 엔티티가 공격하는 원소의 종류를 정합니다.
     * EntityElementSelectionType 이 True 일 경우 날짜마다 엔티티가 가질 원소의 확률이 달라집니다.
     * 아닐 경우 고정된 확률로 발생합니다.
     */
    @Override
    public void initElement() {
        HashMap<Elements, Integer> percent;
        int roll = (int) (Math.random() * 100);
        boolean gameRule = provider.getWorld().getGameRules().getBoolean(QfStatSystemGameRules.ENTITY_ELEMENT_SELECTION_TYPE);
        if (gameRule) {
            long time = provider.getWorld().getTimeOfDay();
            long date = time % 24000;
            int day = (int) (date % 7);
             percent = switch (day) {
                case 1 -> setPeakElement(PHYSICAL);
                case 2 -> setPeakElement(FIRE);
                case 3 -> setPeakElement(WATER);
                case 4 -> setPeakElement(EARTH);
                case 5 -> setPeakElement(LIGHT);
                case 6 -> setPeakElement(DARK);
                default -> getPercent();
            };
        } else percent = getPercent();
        for (Map.Entry<Elements, Integer> elem : percent.entrySet()) {
            if (roll <= elem.getValue()) {
                this.attackElement = elem.getKey();
                return;
            }
            roll -= elem.getValue();
        }
    }

    @Override
    public HashMap<StatEnums, Integer> getDefensiveMap() {
        return this.defensiveMap;
    }

    @Override
    public void serverTick() {

        tryHealthIncrease();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.level = tag.getInt("level");
        this.healthIncreased = tag.getBoolean("healthIncreased");
        this.rank = getRankById(tag.getString("rank"));

        NbtCompound armorStat = (NbtCompound) tag.get("armorStat");
        this.setDefense(armorStat.getInt(ARMOR.getName()));
        this.setDodge(armorStat.getInt(DODGE.getName()));
        this.setFireResistance(armorStat.getInt(FIRE_RESISTANCE.getName()));
        this.setWaterResistance(armorStat.getInt(WATER_RESISTANCE.getName()));
        this.setEarthResistance(armorStat.getInt(EARTH_RESISTANCE.getName()));
        this.setLightResistance(armorStat.getInt(LIGHT_RESISTANCE.getName()));
        this.setDarkResistance(armorStat.getInt(DARK_RESISTANCE.getName()));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("level", this.level);
        tag.putBoolean("healthIncreased", this.healthIncreased);

        NbtCompound armorStat = new NbtCompound();
        this.defensiveMap.forEach((statEnums, value) -> armorStat.putInt(statEnums.getName(), value));
        tag.put("armorStat", armorStat);
        tag.putString("rank", this.rank.getName());
    }

    private void initDefensiveMap() {
        StatEnums[] defensiveMap = StatEnums.getDefensiveStats(false);
        for (StatEnums stat : defensiveMap) {
            this.defensiveMap.put(stat, 0);
        }
    }

    @Override
    public int getDefense() {
        return defensiveMap.get(ARMOR);
    }

    @Override
    public void setDefense(int val) {
        this.defensiveMap.replace(ARMOR, val);
    }

    @Override
    public void addDefense(int val) {
        this.defensiveMap.replace(ARMOR, val + getDefense());
    }

    @Override
    public int getDodge() {
        return this.defensiveMap.get(DODGE);
    }

    @Override
    public void setDodge(int val) {
        this.defensiveMap.replace(DODGE, MathHelper.clamp(val, 0, 100));
    }

    @Override
    public void addDodge(int val) {
        this.defensiveMap.replace(DODGE, MathHelper.clamp(val + getDodge(), 0, 100));
    }

}
