package net.biryeongtrain06.qf_stat_mod.api;


import lombok.Getter;
import lombok.Setter;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.NonePlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.*;

@SuppressWarnings("unused")
public class PlayerStat{
    @Getter
    private int level;
    @Getter
    private float xp;
    @Getter
    private int maxHealth;
    @Getter
    private int healthBaseValue = 100;
    @Getter
    private float healthPercent = 1;
    @Getter
    private float healthMulti = 1;
    @Getter
    private float currentHealth;
    @Getter
    @Setter
    private float regenHealthPerSecond;
    @Getter
    private int maxMana;
    @Getter
    private float currentMana;
    private boolean isManaUser;
    @Getter
    @Setter
    private int armor;
    @Getter
    private int dodge;
    @Getter
    private float fire_resistance;
    @Getter
    private float water_resistance;
    @Getter
    private float earth_resistance;
    @Getter
    private float light_resistance;
    @Getter
    private float dark_resistance;
    @Getter
    @Setter
    private float projectileDamageFlat;
    @Getter
    @Setter
    private float projectileDamagePercent;
    @Getter
    @Setter
    private float projectileDamageMulti;
    private float needXpToLevelUp;
    @Getter
    @Setter
    private int selectPoint;
    @Getter
    private int strength = 0;
    @Getter
    private int dexterity = 0;
    @Getter
    private int intelligence = 0;
    @Getter
    private int wisdom = 0;

    private String playerClassId;


    public PlayerStat(ServerPlayerEntity player) {
        var noneClass = new NonePlayerClass();
        this.playerClassId = noneClass.getClassId().toString();
        this.level = 1;
        this.xp = 1;
        this.maxHealth = 100;
        this.currentHealth = getMaxHealth();
        this.regenHealthPerSecond = 1f;
        this.maxMana = 100;
        this.currentMana = getMaxMana();
        this.isManaUser = true;
        this.armor = 0;
        this.dodge = 0;
        this.fire_resistance = 0;
        this.water_resistance = 0;
        this.earth_resistance = 0;
        this.light_resistance = 0;
        this.dark_resistance = 0;
        this.projectileDamageFlat = 0;
        this.projectileDamagePercent = 0;
        this.projectileDamageMulti = 0;
        this.needXpToLevelUp = ExpHandler.getBaseLevelUpXpValue();
        this.selectPoint = 5;
    }
    public void setPlayer_class(IPlayerClass player_class) {
        this.playerClassId = player_class.getClassId().toString();

    }
    public Identifier getPlayerClassId() {
        return new Identifier(playerClassId);
    }

    public void setPlayerClassId(Identifier playerClassId) {
        this.playerClassId = playerClassId.toString();
    }

    public void addXP(ServerPlayerEntity player, float i) {
        this.xp += i;

        if (xp >= needXpToLevelUp) {
            this.xp-= needXpToLevelUp;
            addLevel(player, 1);
            this.needXpToLevelUp = (float) (ExpHandler.getBaseLevelUpXpValue() * Math.pow(1 + ExpHandler.getLevelScaleModifier(), getLevel()));
        }
    }

    public float getNeedXpToLevelUp() {
        dumpNeedXpToLevelUp();
        return this.needXpToLevelUp;
    }

    public void dumpNeedXpToLevelUp() {
        this.needXpToLevelUp = (float) (ExpHandler.getBaseLevelUpXpValue() * Math.pow(1 + ExpHandler.getLevelScaleModifier(), getLevel()));
    }


    public void addLevel(ServerPlayerEntity player, int i) {
        this.level += i;
        player.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.levelUp")).formatted(Formatting.GREEN));
        addSelectPoint(ExpHandler.getAmountSelectionPointWhenLevelUp());
        dumpNeedXpToLevelUp();
    }

    public void setLevel(int i) {
        this.level = i;
        dumpNeedXpToLevelUp();
    }


    public void setMaxHealth(ServerPlayerEntity player, int amount) {
        amount = MathHelper.clamp(amount, 1, Integer.MAX_VALUE);
        this.maxHealth = amount;
        if (this.maxHealth < this.currentHealth) {
            this.currentHealth = maxHealth;
        }
        syncWithVanillaHealth(player);
    }

    public void addMaxHealth(ServerPlayerEntity player, int amount) {
        this.maxHealth = MathHelper.clamp(this.maxHealth + amount, 1, Integer.MAX_VALUE);
        if (this.maxHealth < this.currentHealth) {
            this.currentHealth = maxHealth;
        }
        syncWithVanillaHealth(player);
    }

    public void setCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(amount, 0f, (float) getMaxHealth());
        syncWithVanillaHealth(player);
    }

    public void addCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth += amount;
        this.currentHealth = MathHelper.clamp(this.currentHealth, 0f, (float) getMaxHealth());
        syncWithVanillaHealth(player);
    }

    public void setDodge(int value) {
        this.dodge = MathHelper.clamp(value, 0, 90);
    }

    public void addDodge(int value) {
        this.dodge += MathHelper.clamp(value, 0, 90);
    }

    public void setFire_resistance(float fire_resistance) {
        this.fire_resistance = Math.min(fire_resistance, 75f);
    }

    public void setWater_resistance(float water_resistance) {
        this.water_resistance = Math.min(water_resistance, 75f);
    }

    public void setEarth_resistance(float earth_resistance) {
        this.earth_resistance = Math.min(earth_resistance, 75f);
    }

    public void setLight_resistance(float light_resistance) {
        this.light_resistance = Math.min(light_resistance, 75f);
    }

    public void setDark_resistance(float dark_resistance) {
        this.dark_resistance = Math.min(dark_resistance, 75f);
    }

    public void addCurrentMana(float val) {
        if (!isManaUser) {
            return;
        }
        this.currentMana = MathHelper.clamp(this.currentMana + val, 0F, getMaxMana());
    }

    public void setCurrentMana(float val) {
        if (!isManaUser) {
            return;
        }
        this.currentMana = MathHelper.clamp(val, 0F, getMaxMana());
    }

    public void addMaxMana(int val) {
        this.maxMana = MathHelper.clamp(this.maxMana + val, 0, Integer.MAX_VALUE);
    }

    public void setMaxMana(int val) {
        if (val <= 0) {
            val = MathHelper.clamp(val, 0, Integer.MAX_VALUE);
        }
        this.maxMana = val;
    }

    public void addSelectPoint(int value) {
        this.selectPoint += value;
    }


    public void damageHealth(DamageSource s, ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(this.currentHealth - amount, 0f, (float) getMaxHealth());
        float calculatedDamage = (amount / getMaxHealth()) * player.getMaxHealth();
        player.hurtTime = 0;
        player.damage(s, calculatedDamage);
    }
    public void syncWithVanillaHealth(ServerPlayerEntity player) {
        if (player.isDead()) {
            return;
        }
        player.setHealth(MathHelper.clamp((float) Math.floor(getCurrentHealth() / getMaxHealth() * 20), 0f, player.getMaxHealth()));
    }

    public boolean hasSelectPoint() {
        return this.selectPoint >= 1;
    }

    public boolean tryRemoveSelectPoint() {
        if (hasSelectPoint()) {
            this.setSelectPoint(MathHelper.clamp(this.getSelectPoint() - 1, 0, Integer.MAX_VALUE));
            return true;
        }
        return false;
    }

    public void addStrength(ServerPlayerEntity player, int value) {
        int changedValue = 0;
        if (this.strength != 0) {
            changedValue -= strength * 2;
        }
        this.strength = MathHelper.clamp(strength + value, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (strength != 0) {
            changedValue += strength * 2;
        }
        addHealthBaseValue(player, changedValue);
    }

    public void addDexterity(int value) {
        int changedValue = 0;
        if (this.dexterity != 0) {
            changedValue -= this.dexterity;
        }
        this.dexterity = MathHelper.clamp(dexterity + value, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.dexterity != 0) {
            changedValue += dexterity;
        }
        addDodge(changedValue);
    }

    public void addWisdom(int value) {
        int changedValue = 0;
        if (this.wisdom != 0) {
            changedValue -= this.wisdom * 7;
        }
        this.wisdom = MathHelper.clamp(this.wisdom + value, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.wisdom != 0) {
            changedValue += this.wisdom * 7;
        }
        addMaxMana(changedValue);
    }

    private boolean isFullHealth() {
        return this.maxHealth == this.currentHealth;
    }

    public void calculateMaxHealth(ServerPlayerEntity player) {
        boolean isFullHealth = isFullHealth();
        int originalHealth = this.maxHealth;
        setMaxHealth(player, Math.round(this.healthBaseValue * this.healthPercent * this.healthMulti));
        int overDamagedRevisionValue = maxHealth - originalHealth;
        if (isFullHealth && overDamagedRevisionValue > 0) {
            setCurrentHealth(player, originalHealth);
        }
    }

    public void setHealthBaseValue(ServerPlayerEntity player, int healthBaseValue) {
        this.healthBaseValue = MathHelper.clamp(healthBaseValue, 1, 1000000);
        calculateMaxHealth(player);
    }

    public void addHealthBaseValue(ServerPlayerEntity player, int value) {
        setHealthBaseValue(player, this.healthBaseValue + value);
    }

    public void setHealthPercent(ServerPlayerEntity player, float healthPercent) {
        this.healthPercent = MathHelper.clamp(healthPercent, 0.01f, 5000f);
        calculateMaxHealth(player);
    }

    public void setHealthMulti(ServerPlayerEntity player, float healthMulti) {
        this.healthMulti = MathHelper.clamp(healthMulti, 0.01f, 5000f);
        calculateMaxHealth(player);
    }

    public EnumMap<StatTypes, Number> getMap() {
        EnumMap<StatTypes, Number> map = new EnumMap<>(StatTypes.class);
        map.put(HEALTH_FLAT, this.healthBaseValue);
        map.put(HEALTH_INCREASE_PERCENT, this.healthPercent);
        map.put(HEALTH_INCREASE_MULTI, this.healthMulti);
        map.put(MANA, this.maxMana);
        map.put(ARMOR, this.armor);
        map.put(DODGE, this.dodge);
        map.put(FIRE_RESISTANCE, this.fire_resistance);
        map.put(WATER_RESISTANCE, this.water_resistance);
        map.put(EARTH_RESISTANCE, this.earth_resistance);
        map.put(LIGHT_RESISTANCE, this.light_resistance);
        map.put(DARK_RESISTANCE, this.dark_resistance);
        map.put(PROJECTILE_DAMAGE_FLAT, this.projectileDamageFlat);
        map.put(PROJECTILE_DAMAGE_PERCENT, this.projectileDamagePercent);
        map.put(PROJECTILE_DAMAGE_MULTI, this.projectileDamageMulti);


        return map;
    }

    public void setStatsByMap(ServerPlayerEntity player, EnumMap<StatTypes, Number> map) {
        this.setHealthBaseValue(player, Math.max(1, map.get(HEALTH_FLAT).intValue()));
        this.setHealthPercent(player, map.get(HEALTH_INCREASE_PERCENT).floatValue());
        this.setHealthMulti(player, map.get(HEALTH_INCREASE_MULTI).floatValue());
        this.setMaxMana(map.get(MANA).intValue());
        this.setDodge(map.get(DODGE).intValue());
        this.setArmor(map.get(ARMOR).intValue());
        this.setFire_resistance(map.get(FIRE_RESISTANCE).floatValue());
        this.setWater_resistance(map.get(WATER_RESISTANCE).floatValue());
        this.setEarth_resistance(map.get(EARTH_RESISTANCE).floatValue());
        this.setLight_resistance(map.get(LIGHT_RESISTANCE).floatValue());
        this.setDark_resistance(map.get(DARK_RESISTANCE).floatValue());
        this.setProjectileDamageFlat( map.get(PROJECTILE_DAMAGE_FLAT).floatValue());
        this.setProjectileDamageMulti(map.get(PROJECTILE_DAMAGE_MULTI).floatValue());
        this.setProjectileDamagePercent(map.get(PROJECTILE_DAMAGE_PERCENT).floatValue());

    }
}
