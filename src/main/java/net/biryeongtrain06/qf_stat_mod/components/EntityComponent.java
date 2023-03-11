package net.biryeongtrain06.qf_stat_mod.components;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.entity.BaseEntityModifiers;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemGameRules;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.EntityRank;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.ENTITY_INIT_STATS;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.Elements.*;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.Elements.getPercent;

public class EntityComponent implements INewCommonEntityComponents{
    private EnumMap<StatTypes, IStats> instance;
    private final MobEntity provider;
    private int level;
    private EntityRank rank = EntityRank.UN_DECIDED;
    private boolean healthIncreased = false;
    private boolean damageIncreased = false;
    private Elements element = Elements.PHYSICAL;


    public EntityComponent(MobEntity provider) {
        this.provider = provider;
        this.instance = setEntityStat(EntityType.getId(provider.getType()));
        setRankRandomly();
        setInitLevel();
        initElement();
    }

    private EnumMap<StatTypes, IStats> setEntityStat(Identifier id) {
        EnumMap<StatTypes, IStats> map = ENTITY_INIT_STATS.get(TextHelper.getId("default_mob"));
        EnumMap<StatTypes, IStats> replaceMap = ENTITY_INIT_STATS.get(id);

        if (replaceMap.isEmpty()) return map;
        replaceMap.keySet().forEach(key -> map.put(key, replaceMap.get(key)));
        return map;
    }

    private void setInitLevel() {
        if (provider.world.isClient) {
            return;
        }
        final int MAX_LEVEL = ExpHandler.getMaxLevel();
        if (provider instanceof EnderDragonEntity) {
            this.level = (int) (MAX_LEVEL / 0.8);
        }
        if (provider instanceof WitherEntity) {
            this.level =  (int) (MAX_LEVEL / 0.6);
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

    private void tryIncreaseHealth() {
        if (healthIncreased) {
            return;
        }
        int value = Math.round(provider.getMaxHealth() * level * rank.getStatMultiplier());
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance.hasModifier(BaseEntityModifiers.getBaseModifier())) entityAttributeInstance.addPersistentModifier(BaseEntityModifiers.getBaseModifier(value));

        provider.setHealth((float) provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
        provider.getWorld().getServer().sendMessage(Text.literal("Health : " + provider.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH)));
    }

    private void tryDamageIncrease() {
        if (damageIncreased) {
            return;
        }
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        float originalDamage = (float) provider.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (!entityAttributeInstance.hasModifier(BaseEntityModifiers.getBaseModifier())) entityAttributeInstance.addPersistentModifier(BaseEntityModifiers.getBaseModifier(Math.round(originalDamage * this.rank.getStatMultiplier())));
        this.damageIncreased = true;
        provider.getWorld().getServer().sendMessage(Text.literal(("Damage : " + provider.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE))));
    }

    private void initElement() {
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
                this.element = elem.getKey();
                return;
            }
            roll -= elem.getValue();
        }
    }

    @Override
    public IStats getStatClass(StatTypes stat) {
        return instance.get(stat);
    }

    @Override
    public void setBaseHealth(int value) {
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        entityAttributeInstance.removeModifier(BaseEntityModifiers.getBaseModifier());
        entityAttributeInstance.addPersistentModifier(BaseEntityModifiers.getBaseModifier(value));
    }

    @Override
    public void addPersistentHealthInstance(EntityAttributeModifier modifier) {
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        entityAttributeInstance.addPersistentModifier(modifier);
    }

    @Override
    public void addTemporaryHealthInstance(EntityAttributeModifier modifier) {
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        entityAttributeInstance.addTemporaryModifier(modifier);
    }

    @Override
    public void setBaseDamage(int value) {
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        entityAttributeInstance.removeModifier(BaseEntityModifiers.getBaseModifier());
        entityAttributeInstance.addPersistentModifier(BaseEntityModifiers.getBaseModifier(value));
    }

    @Override
    public void addPersistentDamageInstance(EntityAttributeModifier modifier) {
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        entityAttributeInstance.addPersistentModifier(modifier);
    }

    @Override
    public void addTemporaryDamageInstance(EntityAttributeModifier modifier) {
        EntityAttributeInstance entityAttributeInstance = provider.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        entityAttributeInstance.addTemporaryModifier(modifier);
    }


    @Override
    public void setLevel(int value) {
        this.level = value;
    }

    @Override
    public void setElement(Elements element) {
        this.element = element;
    }

    @Override
    public void serverTick() {
        tryIncreaseHealth();
        tryDamageIncrease();
    }

    @Override
    public void addStatInstance(StatTypes type, Identifier id, float value, StatSubTag tag) {
        if (checkStatUseOnlyPlayer(type)) return;
        IStats stat = instance.get(type);
        stat.addStatInstance(id, value, tag);
    }

    private boolean checkStatUseOnlyPlayer(StatTypes type) {
        return type.isPlayerStat();
    }
    @Override
    public boolean replaceStatInstance(StatTypes type, Identifier id, float value, StatSubTag tag) {
        IStats stat = instance.get(type);
        return stat.tryReplaceInstance(id, value, tag);
    }
    @Override
    public boolean hasStatInstance(StatTypes type, Identifier id, StatSubTag tag) {
        IStats stat = instance.get(type);
        return stat.hasInstance(id, tag);
    }

    @Override
    public float getTotalStatValue(StatTypes type) {
        IStats stat = instance.get(type);
        return stat.getTotalValue();
    }



    @Override
    public void readFromNbt(NbtCompound tag) {
        this.healthIncreased = tag.getBoolean("healthIncreased");
        this.damageIncreased = tag.getBoolean("damageIncreased");
        this.level = tag.getInt("level");
        this.instance = ConvertNbtCompoundAsMap(tag);
        this.element = Elements.getElementWithId(new Identifier(tag.getString("element")));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("healthIncreased", healthIncreased);
        tag.putBoolean("damageIncreased", damageIncreased);
        tag.putInt("level", level);
        tag.put("stat", ConvertMapAsNbtCompound());
        tag.putString("element", element.getId().toString());
    }

    @Override
    public Elements getElement() {
        return this.element;
    }

    private NbtCompound ConvertMapAsNbtCompound() {
        NbtCompound nbtCompound = new NbtCompound();
        instance.forEach(((statTypes, iStats) -> {
            // HEALTH, ARMOR etc...
            String typeName = statTypes.getName();
            NbtCompound nbtCompound1 = new NbtCompound();
            iStats.getCloneMap().forEach((tag, map) -> {
                // FLAT, PERCENT, MULTI
                NbtCompound nbtCompound2 = new NbtCompound();
                map.forEach((id, value) -> {
                    // id, value
                    nbtCompound2.putFloat(id.toString(), value);
                });
                nbtCompound1.put(tag.name(), nbtCompound2);
            });
            nbtCompound.put(typeName ,nbtCompound1);
        }));
        return nbtCompound;
    }

    private EnumMap<StatTypes, IStats> ConvertNbtCompoundAsMap(NbtCompound nbtCompound) {
        EnumMap<StatTypes, IStats> map = new EnumMap<>(StatTypes.class);
        nbtCompound.getKeys().forEach(stat -> {

            StatTypes type = StatTypes.getStatByName(stat);
            IStats statClazz;
            NbtCompound nbtCompound2 = nbtCompound.getCompound(stat); // FLAT, PERCENT, MULTI
            if (nbtCompound2.getSize() == 3) statClazz = new FloatStat();
            else statClazz = new PercentStat();

            nbtCompound2.getKeys().forEach(tag -> {
                NbtCompound nbtCompound3 = nbtCompound2.getCompound(tag); // ID , value

                nbtCompound3.getKeys().forEach(id -> statClazz.addStatInstance(new Identifier(id), nbtCompound3.getFloat(id), StatSubTag.getStatByName(tag)));
            });
            map.put(type, statClazz);
        });
        return map;
    }
}
