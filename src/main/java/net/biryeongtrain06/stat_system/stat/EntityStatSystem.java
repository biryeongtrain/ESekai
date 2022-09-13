package net.biryeongtrain06.stat_system.stat;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.biryeongtrain06.stat_system.component.EntityStatComponentInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;

import static net.biryeongtrain06.stat_system.stat.PlayerStat.*;

public class EntityStatSystem extends EntityStat implements EntityStatComponentInterface, AutoSyncedComponent {

    private final Entity entity;

    public EntityStatSystem(int health, int defense, int dodge, int attack_damage, int level, int difficulty, int fire_resistance, int water_resistance, int earth_resistance, int light_resistance, int dark_resistance, HostileEntity entity) {
        super(health, defense, dodge, attack_damage, level, difficulty, fire_resistance, water_resistance, earth_resistance, light_resistance, dark_resistance);
        this.entity = entity;
    }

    public EntityStatSystem(Entity entity) {
        super(20, 0, 0, 1, 1, 0, 0, 0, 0,0,0);
        this.entity = entity;
    }


    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
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
    }
}
