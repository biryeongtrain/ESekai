package net.biryeongtrain06.stat_system.stat;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.biryeongtrain06.stat_system.component.PlayerStatComponentInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerStatSystem extends PlayerStat implements PlayerStatComponentInterface, AutoSyncedComponent {

    private final PlayerEntity player;
    private final int POINT_PER_LEVEL = 5;
    private final int STR_HEALTH_MODIFIER = 2;
    private final double STR_ATTACK_DMG_MODIFIER = 0.15;
    private final int DEX_DODGE_MODIFIER = 3;
    private final double DEX_ATTACK_DMG_MODIFIER = 0.1;
    private final int INT_MAX_MANA_MODIFIER = 10;
    private final int INT_DEFENSE_MODIFIER = 2;
    private final int LUCK_DODGE_MODIFIER = 2;

    public PlayerStatSystem(int xp, int level,int statPoint, int strength, int dexterity, int intelligence, int luck, int health, int defense, int dodge, int mana, double magic_damage, double attack_damage, int fire_resistance, int water_resistance, int earth_resistance, int light_resistance, int dark_resistance, ServerPlayerEntity player) {
        super(xp, level, strength, statPoint, dexterity, intelligence, luck, health, defense, dodge, mana, magic_damage, attack_damage, fire_resistance, water_resistance, earth_resistance, light_resistance, dark_resistance);
        this.player = player;
    }

    public PlayerStatSystem(PlayerEntity player) {
        super(0, 1, 5, 0, 0, 0, 0, 20, 0, 0, 10, 1, 1, 0, 0, 0, 0, 0);
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
        this.health = tag.getInt(HEALTH_KEY);
        this.defense = tag.getInt(DEFENSE_KEY);
        this.dodge = tag.getInt(DODGE_KEY);
        this.mana = tag.getInt(MANA_KEY);
        this.magic_damage = tag.getDouble(MAGIC_DAMAGE_KEY);
        this.attack_damage = tag.getDouble(ATTACK_DAMAGE_KEY);
        this.xp = tag.getInt(XP_KEY);
        this.level = tag.getInt(LEVEL_KEY);
        this.strength = tag.getInt(STRENGTH_KEY);
        this.dexterity = tag.getInt(Dexterity_KEY);
        this.intelligence = tag.getInt(INTELLIGENCE_KEY);
        this.luck = tag.getInt(LUCK_KEY);
        this.statPoint = tag.getInt(STAT_POINT_KEY);
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
        tag.putInt(MANA_KEY, this.mana);
        tag.putDouble(MAGIC_DAMAGE_KEY, this.magic_damage);
        tag.putDouble(ATTACK_DAMAGE_KEY, this.attack_damage);
        tag.putInt(XP_KEY, this.xp);
        tag.putInt(LEVEL_KEY, this.level);
        tag.putInt(STRENGTH_KEY, this.strength);
        tag.putInt(DEFENSE_KEY, this.defense);
        tag.putInt(INTELLIGENCE_KEY, this.intelligence);
        tag.putInt(LUCK_KEY, this.luck);
        tag.putInt(STAT_POINT_KEY, this.statPoint);
        tag.putInt(FIRE_RESISTANCE_KEY, this.fire_resistance);
        tag.putInt(WATER_RESISTANCE_KEY, this.fire_resistance);
        tag.putInt(EARTH_RESISTANCE_KEY, this.earth_resistance);
        tag.putInt(LIGHT_RESISTANCE_KEY, this.light_resistance);
        tag.putInt(DARK_RESISTANCE_KEY, this.dark_resistance);
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player.getUuid().equals(this.player.getUuid());
    }
}
