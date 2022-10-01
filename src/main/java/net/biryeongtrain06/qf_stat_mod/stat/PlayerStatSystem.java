package net.biryeongtrain06.qf_stat_mod.stat;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.biryeongtrain06.qf_stat_mod.component.PlayerBaseStatComponentInterface;
import net.biryeongtrain06.qf_stat_mod.util.enums.Stats;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import static net.biryeongtrain06.qf_stat_mod.util.enums.Stats.*;
import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MAX_HEALTH;

public class PlayerStatSystem extends PlayerStat implements PlayerBaseStatComponentInterface, AutoSyncedComponent {

    private final PlayerEntity player;
    private final int POINT_PER_LEVEL = 5;
    private final int STR_HEALTH_MODIFIER = 2;
    private final double STR_ATTACK_DMG_MODIFIER = 0.15;
    private final int DEX_DODGE_MODIFIER = 3;
    private final double DEX_ATTACK_DMG_MODIFIER = 0.1;
    private final int INT_MAX_MANA_MODIFIER = 10;
    private final int INT_DEFENSE_MODIFIER = 2;
    private final int LUCK_DODGE_MODIFIER = 2;

    public PlayerStatSystem(int xp, int level,int statPoint, int strength, int dexterity, int intelligence, int luck, int health, int defense, int dodge, int mana, double magic_damage, double attack_damage, int fire_resistance, int water_resistance, int earth_resistance, int light_resistance, int dark_resistance, int reduce_physical_dmg,ServerPlayerEntity player) {
        super(xp, level, strength, statPoint, dexterity, intelligence, luck, health, defense, dodge, mana, magic_damage, attack_damage, fire_resistance, water_resistance, earth_resistance, light_resistance, dark_resistance, reduce_physical_dmg);
        this.player = player;
    }

    public PlayerStatSystem(PlayerEntity player) {
        super(0, 1, 5, 0, 0, 0, 0, 20, 0, 0, 10, 1, 1, 0, 0, 0, 0, 0, 0);
        this.player = player;
    }

    @Override
    public void addStatPoint(int statPoint) {
        this.statPoint += statPoint;
    }

    @Override
    public void setStatPoint(int statPoint) {
        this.statPoint = statPoint;
    }

    @Override
    public void addStrength(int strength) {
        this.strength += strength;
        addMaxHealth(STR_HEALTH_MODIFIER);
        addAttack_damage(STR_ATTACK_DMG_MODIFIER);
    }

    @Override
    public void setStrength(int strength) {
        addMaxHealth(-this.strength * STR_HEALTH_MODIFIER);
        addAttack_damage(-this.strength * STR_ATTACK_DMG_MODIFIER);
        this.strength = strength;

        addMaxHealth(this.strength * STR_HEALTH_MODIFIER);
        addAttack_damage(this.strength * STR_ATTACK_DMG_MODIFIER);
    }

    @Override
    public void addDexterity(int dexterity) {
        addDodge(DEX_DODGE_MODIFIER);
        addAttack_damage(DEX_ATTACK_DMG_MODIFIER);
        this.dexterity += dexterity;
    }

    @Override
    public void setDexterity(int dexterity) {
        addDodge(-this.dexterity * DEX_DODGE_MODIFIER);
        addAttack_damage(-this.dexterity * DEX_ATTACK_DMG_MODIFIER);
        this.dexterity = dexterity;
        addDodge(this.dexterity * DEX_DODGE_MODIFIER);
        addAttack_damage(this.dexterity * DEX_ATTACK_DMG_MODIFIER);
    }

    @Override
    public void addIntelligence(int intelligence) {
        this.intelligence += intelligence;
        addMaxMana(INT_MAX_MANA_MODIFIER);
        addDefense(INT_DEFENSE_MODIFIER);
    }

    @Override
    public void setIntelligence(int intelligence) {
        addMaxMana(-this.intelligence * INT_MAX_MANA_MODIFIER);
        addDefense(-this.intelligence * INT_DEFENSE_MODIFIER);
        this.intelligence = intelligence;
        addMaxMana(this.intelligence * INT_MAX_MANA_MODIFIER);
        addDefense(this.intelligence * INT_DEFENSE_MODIFIER);
    }

    @Override
    public void addLuck(int luck) {
        this.luck += luck;
        //TODO : Add Critical Rate and Apply to here.
        addDodge(LUCK_DODGE_MODIFIER);
    }

    @Override
    public void setLuck(int luck) {
        addDodge(-this.luck * LUCK_DODGE_MODIFIER);
        this.luck = luck;
        addDodge(this.luck * LUCK_DODGE_MODIFIER);
    }


    @Override
    public void setMaxHealth(int health) {
        this.health = health;
        EntityAttributeInstance entityAttributeInstance = player.getAttributeInstance(GENERIC_MAX_HEALTH);
        if (player.getHealth() == health) {
            player.setHealth(health);
        }
        entityAttributeInstance.setBaseValue(health);
    }

    @Override
    public void addMaxHealth(int health) {
        this.health += health;
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
    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    @Override
    public void addDodge(int dodge) {
        this.dodge += dodge;
    }

    @Override
    public void setMaxMana(int mana) {
        this.mana = mana;
    }

    @Override
    public void addMaxMana(int mana) {
        this.mana += mana;
    }

    @Override
    public void setMagic_damage(double magic_damage) {
        this.magic_damage = magic_damage;
    }

    @Override
    public void addMagic_damage(double magic_damage) {
        this.magic_damage += magic_damage;
    }

    @Override
    public void setAttack_damage(double attack_damage) {
        this.attack_damage = attack_damage;
    }

    @Override
    public void addAttack_damage(double attack_damage) {
        this.attack_damage += attack_damage;
    }

    @Override
    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public void setLevel(int level) {
        addStatPoint(-POINT_PER_LEVEL * this.level);
        this.level = level;
        addStatPoint(POINT_PER_LEVEL * this.level);
    }

    @Override
    public void addLevel(int level) {
        this.level += level;
        this.player.sendMessage(Text.literal("Level Up! Now your Level is " + this.level));
        addStatPoint(POINT_PER_LEVEL);
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
    public void addXp(int xp) {
        this.xp += xp;
        if (this.xp > 1) {
            return;
        }
        while (this.xp >= 1) {
            this.addLevel(1);
            this.xp -= 1;
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.health = tag.getInt(Health.key);
        this.defense = tag.getInt(Defense.key);
        this.dodge = tag.getInt(Dodge.key);
        this.mana = tag.getInt(Mana.key);
        this.magic_damage = tag.getDouble(Magic_Damage.key);
        this.attack_damage = tag.getDouble(Attack_Damage.key);
        this.xp = tag.getInt(XP_KEY);
        this.level = tag.getInt(LEVEL_KEY);
        this.strength = tag.getInt(Stats.Strength.key);
        this.dexterity = tag.getInt(Stats.Dexterity.key);
        this.intelligence = tag.getInt(Stats.Intelligence.key);
        this.luck = tag.getInt(Stats.Luck.key);
        this.statPoint = tag.getInt(STAT_POINT_KEY);
        this.fire_resistance = tag.getInt(Stats.Fire_Resistance.key);
        this.water_resistance = tag.getInt(Stats.Water_Resistance.key);
        this.earth_resistance = tag.getInt(Stats.Earth_Resistance.key);
        this.light_resistance = tag.getInt(Stats.Light_Resistance.key);
        this.dark_resistance = tag.getInt(Stats.Dark_Resistance.key);
        this.reduce_physical_dmg = tag.getInt(Stats.Reduce_Physical_DMG.key);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(Health.key, this.health);
        tag.putInt(Defense.key, this.defense);
        tag.putInt(Dodge.key, this.dodge);
        tag.putInt(Mana.key, this.mana);
        tag.putDouble(Magic_Damage.key ,this.magic_damage);
        tag.putDouble(Attack_Damage.key, this.attack_damage);
        tag.putInt(XP_KEY, this.xp);
        tag.putInt(LEVEL_KEY, this.level);
        tag.putInt(Strength.key, this.strength);
        tag.putInt(Dexterity.key, this.dexterity);
        tag.putInt(Intelligence.key, this.intelligence);
        tag.putInt(Luck.key, this.luck);
        tag.putInt(STAT_POINT_KEY, this.statPoint);
        tag.putInt(Fire_Resistance.key, this.fire_resistance);
        tag.putInt(Water_Resistance.key, this.fire_resistance);
        tag.putInt(Earth_Resistance.key, this.earth_resistance);
        tag.putInt(Light_Resistance.key, this.light_resistance);
        tag.putInt(Dark_Resistance.key, this.dark_resistance);
        tag.putInt(Reduce_Physical_DMG.key, this.reduce_physical_dmg);
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player.getUuid().equals(this.player.getUuid());
    }
}
