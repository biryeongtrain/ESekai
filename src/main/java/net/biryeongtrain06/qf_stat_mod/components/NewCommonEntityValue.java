package net.biryeongtrain06.qf_stat_mod.components;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemGameRules;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.EntityRank;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
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

import static net.biryeongtrain06.qf_stat_mod.utils.enums.Elements.*;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.EntityRank.getRankById;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.*;

/**
 * 엔티티에게만 적용되는 Cardinal Components용 Class 입니다.
 */
public class NewCommonEntityValue implements INewCommonEntityComponents {
    private final MobEntity provider;
    private final Object2ObjectOpenHashMap<StatTypes, IStats> map = new Object2ObjectOpenHashMap<>();
    private int level = 1;
    private Elements attackElement = PHYSICAL;
    private EntityRank rank = EntityRank.UN_DECIDED;
    private int numMaxAbilities = EntityRank.UN_DECIDED.getAbilities();
    private boolean healthIncreased = false;
    private boolean damageIncreased = false;

    public NewCommonEntityValue(MobEntity provider) {
        this.provider = provider;
        initMap();
        if (canApplyModifier(this.provider)) {
            setRankRandomly();
        } else this.rank = EntityRank.COMMON;
       // setLevel();
    }

    public boolean canApplyModifier(MobEntity provider) {
        return this.rank.equals(EntityRank.UN_DECIDED) && !provider.hasCustomName() && provider instanceof HostileEntity;
    }

    /**
     * 엔티티의 기본적인 등급을 정합니다. 등급은 데이터팩의 rarity.json 을 토대로 작성됩니다.
     *
     * @see EntityRank
     */
    private void setRankRandomly() {
        if (!(this.rank.equals(EntityRank.UN_DECIDED))) {
            return;
        }
        float value = (float) (Math.random() * 100);

        for (EntityRank rank : EntityRank.values()) {
            if (rank == EntityRank.UN_DECIDED) {
                continue;
            }
            if (value > rank.getSpawnChance()) {
                value -= rank.getSpawnChance();
            } else {
                this.rank = rank;
                return;
            }
        }
    }

    private void setModifierRandomly() {
        if (rank != EntityRank.COMMON && rank != EntityRank.UN_DECIDED) {

        }
    }

    /**
     * 엔티티의 체력을 변경합니다. 이미 변경이 완료된 엔티티는 실행되지 않습니다.
     *
     * @see EntityRank EntityRank에서 엔티티의 체력 배수를 볼 수 있습니다.
     */
    private void tryHealthIncrease() {
        if (healthIncreased) {
            return;
        }
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        int randomValue = (int) Math.round(((Math.random() * 0.5) + 0.5));
        int value = (int) MathHelper.clamp(provider.getMaxHealth() * (this.level - 1 + randomValue + this.rank.getStatMultiplier()), provider.getMaxHealth(), provider.getMaxHealth() * this.level * 10);
        if (!entityAttributeInstance.hasModifier(getHealthModifier(rank, value)))
            entityAttributeInstance.addPersistentModifier(getHealthModifier(rank, value));
        provider.setHealth((float) provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
        this.healthIncreased = true;
        provider.getWorld().getServer().sendMessage(Text.literal(("health : " + provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH))));
    }

    private void tryDamageIncrease() {
        if (damageIncreased) {
            return;
        }
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        float originalDamage = (float) provider.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (!entityAttributeInstance.hasModifier(getDamageModifier(this.rank, 0)))
            entityAttributeInstance.addPersistentModifier(getDamageModifier(this.rank, Math.round(originalDamage * this.rank.getStatMultiplier())));
        this.damageIncreased = true;
        provider.getWorld().getServer().sendMessage(Text.literal(("Damage : " + provider.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE))));
    }

    /**
     * @param rank
     * @param value
     * @return rank 에 따른 이름과 value에 따른 수치를 가진 Attribute를 반환합니다.
     */
    public EntityAttributeModifier getHealthModifier(EntityRank rank, int value) {
        return new EntityAttributeModifier(rank.name() + "_HEALTH_BOOST", value, EntityAttributeModifier.Operation.ADDITION);
    }

    public EntityAttributeModifier getDamageModifier(EntityRank rank, int value) {
        return new EntityAttributeModifier(rank.getName() + "_DAMAGE_BOOST", value, EntityAttributeModifier.Operation.ADDITION);
    }

    /**
     * 엔티티의 레벨을 설정합니다. 엔티티의 레벨은 게임 룰에 따라서 변경됩니다.
     * true 일 경우 근처 플레이어의 레벨을 따르고, false 일 경우 스폰 지역에서의 거리를 따릅니다.
     *
     * @see QfStatSystemGameRules
     */
    private void setLevelRandomly() {
        final int MAX_LEVEL = ExpHandler.getMaxLevel();
        if (provider instanceof EnderDragonEntity) {
            this.level = (int) (MAX_LEVEL / 0.8);
        }
        if (provider instanceof WitherEntity) {
            this.level = (int) (MAX_LEVEL / 0.6);
        }
        if (provider.world.isClient) {
            return;
        }

        final int SCALING_DISTANCE = ExpHandler.getScalingDistance();
        boolean gameRule = provider.getWorld().getGameRules().getBoolean(QfStatSystemGameRules.ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING);
        final ServerPlayerEntity nearestPlayer = PlayerHelper.getNearestPlayer((ServerWorld) provider.world, provider);

        if (gameRule && nearestPlayer != null && DataStorage.loadPlayerStat(nearestPlayer) != null) {
            final int nearestPlayerLevel = DataStorage.loadPlayerStat(nearestPlayer).getLevel();
            final int MIN = MathHelper.clamp(nearestPlayerLevel - 5, 1, MAX_LEVEL);
            final int MAX = MathHelper.clamp(nearestPlayerLevel + 5, 0, MAX_LEVEL);
            this.level = (int) Math.round(Math.random() * MAX + MIN);
            return;
        }
        BlockPos spawnPos = provider.getWorld().getSpawnPos();
        double distance = spawnPos.getManhattanDistance(provider.getBlockPos());
        this.level = (int) (distance / SCALING_DISTANCE) + 1;
    }

    /**
     * 엔티티가 공격하는 원소의 종류를 정합니다.
     * EntityElementSelectionType 이 True 일 경우 날짜마다 엔티티가 가질 원소의 확률이 달라집니다.
     * 아닐 경우 고정된 확률로 발생합니다.
     */
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
    public void serverTick() {

        tryHealthIncrease();
        tryDamageIncrease();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.level = tag.getInt("level");
        this.healthIncreased = tag.getBoolean("healthIncreased");
        this.rank = getRankById(tag.getString("rank"));

        NbtCompound armorStat = (NbtCompound) tag.get("armorStat");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("level", this.level);
        tag.putBoolean("healthIncreased", this.healthIncreased);

        NbtCompound armorStat = new NbtCompound();
        tag.put("armorStat", armorStat);
        tag.putString("rank", this.rank.getName());
    }

    private void initMap() {

    }
}
