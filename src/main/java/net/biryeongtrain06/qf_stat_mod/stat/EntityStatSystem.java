package net.biryeongtrain06.qf_stat_mod.stat;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.biryeongtrain06.qf_stat_mod.component.EntityStatComponentInterface;
import net.biryeongtrain06.qf_stat_mod.util.enums.Stats;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

import static net.biryeongtrain06.qf_stat_mod.stat.PlayerStat.*;
import static net.biryeongtrain06.qf_stat_mod.util.MobAttributeModifiers.BASE_HEALTH_FLAT_KEY;

public class EntityStatSystem extends EntityStat implements EntityStatComponentInterface, AutoSyncedComponent {

    private final MobEntity entity;

    public EntityStatSystem(int health, int defense, int dodge, int attack_damage, int level, int difficulty, int fire_resistance, int water_resistance, int earth_resistance, int light_resistance, int dark_resistance, int reduce_physical_dmg, MobEntity entity) {
        super(health, defense, dodge, attack_damage, level, difficulty, fire_resistance, water_resistance, earth_resistance, light_resistance, dark_resistance, reduce_physical_dmg);
        this.entity = entity;
    }

    public EntityStatSystem(MobEntity entity) {
        super(20, 0, 0, 1, 1, 0, 0, 0, 0,0,0, 0);
        this.entity = entity;
    }


    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        if (this.health == health) {
            this.health = health;
            EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (entityAttributeInstance.tryRemoveModifier(BASE_HEALTH_FLAT_KEY)) {
                entity.setHealth(this.health);
            }
            entityAttributeInstance.addPersistentModifier(new EntityAttributeModifier(BASE_HEALTH_FLAT_KEY, "BASE_HEALTH_FLAT", Math.min(this.health - 20, entity.defaultMaxHealth), EntityAttributeModifier.Operation.fromId(0)));
        }
    }

    @Override
    public void addHealth(int health) {
        this.health += health;
    }

    @Override
    public int getDefense() {
        return this.defense;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public void addDefense(int defense) {
        this.defense += defense;
    }

    @Override
    public int getDodge() {
        return this.dodge;
    }

    @Override
    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    @Override
    public void addDodge(int dodge) {
        this.dodge += dodge;
    }

    @Override
    public float getAttack_damage() {
        return this.attack_damage;
    }

    @Override
    public void setAttack_damage(float attack_damage) {
        this.attack_damage = attack_damage;
    }

    @Override
    public void addAttack_damage(float attack_damage) {
        this.attack_damage += attack_damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }


    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void addFireResistance(int value) {
        this.fire_resistance += value;
    }

    @Override
    public void setFireResistance(int value) {
        this.fire_resistance = value;
    }

    @Override
    public void addWaterResistance(int value) {
        this.water_resistance += value;
    }

    @Override
    public void setWaterResistance(int value) {
        this.water_resistance = value;
    }

    @Override
    public void addEarthResistance(int value) {
        this.earth_resistance += value;
    }

    @Override
    public void setEarthResistance(int value) {
        this.earth_resistance = value;
    }

    @Override
    public void addLightResistance(int value) {
        this.light_resistance += value;
    }

    @Override
    public void setLightResistance(int value) {
        this.light_resistance = value;
    }

    @Override
    public void addDarkResistance(int value) {
        this.dark_resistance += value;
    }

    @Override
    public void setDarkResistance(int value) {
        this.dark_resistance = value;
    }

    @Override
    public void addReducePhysicalDMG(int value) {
        this.reduce_physical_dmg = MathHelper.clamp(this.reduce_physical_dmg + value, 0, 75);
    }

    @Override
    public void setReducePhysicalDMG(int value) {
        this.reduce_physical_dmg = MathHelper.clamp(value, 0, 75);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.health = tag.getInt(HEALTH_KEY);
        this.defense = tag.getInt(DEFENSE_KEY);
        this.dodge = tag.getInt(DODGE_KEY);
        this.attack_damage = tag.getFloat(ATTACK_DAMAGE_KEY);
        this.level = tag.getInt(LEVEL_KEY);
        this.difficulty = tag.getInt(MOB_DIFFICULTY_KEY);
        this.fire_resistance = tag.getInt(FIRE_RESISTANCE_KEY);
        this.water_resistance = tag.getInt(WATER_RESISTANCE_KEY);
        this.earth_resistance = tag.getInt(EARTH_RESISTANCE_KEY);
        this.light_resistance = tag.getInt(LIGHT_RESISTANCE_KEY);
        this.dark_resistance = tag.getInt(DARK_RESISTANCE_KEY);
        this.reduce_physical_dmg = tag.getInt(Stats.Reduce_Physical_DMG.key);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(HEALTH_KEY, this.health);
        tag.putInt(DEFENSE_KEY, this.defense);
        tag.putInt(DODGE_KEY, this.dodge);
        tag.putFloat(ATTACK_DAMAGE_KEY, this.attack_damage);
        tag.putInt(LEVEL_KEY, this.level);
        tag.putInt(MOB_DIFFICULTY_KEY, this.difficulty);
        tag.putInt(FIRE_RESISTANCE_KEY, this.fire_resistance);
        tag.putInt(WATER_RESISTANCE_KEY, this.water_resistance);
        tag.putInt(EARTH_RESISTANCE_KEY, this.earth_resistance);
        tag.putInt(LIGHT_RESISTANCE_KEY, this.light_resistance);
        tag.putInt(DARK_RESISTANCE_KEY, this.dark_resistance);
        tag.putInt(Stats.Reduce_Physical_DMG.key, this.reduce_physical_dmg);
    }
}
