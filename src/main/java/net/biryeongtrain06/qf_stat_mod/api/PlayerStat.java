package net.biryeongtrain06.qf_stat_mod.api;


import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.biryeongtrain06.qf_stat_mod.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.playerclass.NonePlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
public class PlayerStat {
    private int level;
    private float xp;
    private int maxHealth;
    private float currentHealth;
    private float regenPerSecond;
    private int maxMana;
    private float currentMana;
    private boolean isManaUser;
    private int armor;
    private int dodge;
    private float fire_resistance;
    private float water_resistance;
    private float earth_resistance;
    private float light_resistance;
    private float dark_resistance;
    private float projectileDamageFlat;
    private float projectileDamagePercent;
    private float projectileDamageMulti;
    private float needXpToLevelUp;
    private int selectPoint;
    private int strength = 0;
    private int dexterity = 0;
    private int intelligence = 0;
    private int wisdom = 0;

    private String playerClassId;


    public PlayerStat() {
        var noneClass = new NonePlayerClass();
        this.playerClassId = noneClass.getClassId().toString();
        this.level = 1;
        this.xp = 1;
        this.maxHealth = 100;
        this.currentHealth = getMaxHealth();
        this.regenPerSecond = 1f;
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
        this.needXpToLevelUp = (float) (ExpHandler.getBaseLevelUpXpValue() * Math.pow(1 + ExpHandler.getLevelScaleModifier(), getLevel()));
        return this.needXpToLevelUp;
    }

    public void setXP(int i) {
        this.xp = i;
    }

    public float getXP() {
        return this.xp;
    }

    public int getLevel() {
        return this.level;
    }

    public void addLevel(ServerPlayerEntity player, int i) {
        this.level += i;
        player.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.levelUp")).formatted(Formatting.GREEN));
        addSelectPoint(ExpHandler.getAmountSelectionPointWhenLevelUp());
    }

    public void setLevel(int i) {
        this.level = i;
    }


    public void setMaxHealth(ServerPlayerEntity player, int amount) throws CommandSyntaxException {
        if (amount <= 0 ) {
            throw new CommandSyntaxException(new SimpleCommandExceptionType(Text.literal("Value can not be under 0.")), Text.literal("Value Can not be under 0"));
        }
        this.maxHealth = amount;
        syncPlayerHealth(player);
    }

    public void addMaxHealth(ServerPlayerEntity player, int amount) {
        this.maxHealth = MathHelper.clamp(this.maxHealth + amount, 1, Integer.MAX_VALUE);
        syncPlayerHealth(player);
    }

    public void setCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(amount, 0f, (float) getMaxHealth());
        syncPlayerHealth(player);
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void addCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth += amount;
        this.currentHealth = MathHelper.clamp(this.currentHealth, 0f, (float) getMaxHealth());
        syncPlayerHealth(player);
    }
    public float getCurrentHealth() {
        return this.currentHealth;
    }

    public float getRegenPerSecond() {
        return regenPerSecond;
    }

    public void setRegenPerSecond(float regenPerSecond) {
        this.regenPerSecond = regenPerSecond;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int value) {
        this.dodge = MathHelper.clamp(value, 0, 90);
    }

    public void addDodge(int value) {
        this.dodge += MathHelper.clamp(value, 0, 90);
    }

    public float getFire_resistance() {
        return fire_resistance;
    }

    public void setFire_resistance(int fire_resistance) {
        this.fire_resistance = fire_resistance;
    }

    public float getWater_resistance() {
        return water_resistance;
    }

    public void setWater_resistance(int water_resistance) {
        this.water_resistance = water_resistance;
    }

    public float getEarth_resistance() {
        return earth_resistance;
    }

    public void setEarth_resistance(int earth_resistance) {
        this.earth_resistance = earth_resistance;
    }

    public float getLight_resistance() {
        return light_resistance;
    }

    public void setLight_resistance(int light_resistance) {
        this.light_resistance = light_resistance;
    }

    public float getDark_resistance() {
        return dark_resistance;
    }

    public void setDark_resistance(int dark_resistance) {
        this.dark_resistance = dark_resistance;
    }

    public float getCurrentMana() {
        return this.currentMana;
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
        this.currentMana = MathHelper.clamp(val, 0F, getCurrentMana());
    }
    public int getMaxMana() {
        return maxMana;
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

    public int getSelectPoint() {
        return this.selectPoint;
    }

    public void setSelectPoint(int value) {
        this.selectPoint = value;
    }

    public void damageHealth(DamageSource s, ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(this.currentHealth - amount, 0f, (float) getMaxHealth());
        float calculatedDamage = (amount / getMaxHealth()) * player.getMaxHealth();
        player.hurtTime = 0;
        player.sendMessage(Text.literal(String.valueOf(amount)));
        player.sendMessage(Text.literal(String.valueOf(calculatedDamage)));
        player.damage(s, calculatedDamage);
    }
    public void syncPlayerHealth(ServerPlayerEntity player) {
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
        addMaxHealth(player, changedValue);
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

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getWisdom() {
        return wisdom;
    }
}
