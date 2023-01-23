package net.biryeongtrain06.qf_stat_mod.api;


import net.biryeongtrain06.qf_stat_mod.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.playerclass.NonePlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.QfCustomDamage;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
    private int maxMana;
    private float currentMana;
    private boolean isManaUser;
    private int defense;
    private float projectileDamageFlat;
    private float projectileDamagePercent;
    private float projectileDamageMulti;
    private float needXpToLevelUp;
    private int selectPoint;

    private String playerClassId;


    public PlayerStat() {
        var noneClass = new NonePlayerClass();
        this.playerClassId = noneClass.getClassId().toString();
        this.level = 1;
        this.xp = 1;
        this.maxHealth = 100;
        this.currentHealth = getMaxHealth();
        this.maxMana = 100;
        this.currentMana = getMaxMana();
        this.isManaUser = true;
        this.defense = 0;
        this.projectileDamageFlat = 0;
        projectileDamagePercent = 0;
        this.projectileDamagePercent = 0;
        projectileDamageMulti = 0;
        this.projectileDamageMulti = 0;
        this.needXpToLevelUp = ExpHandler.getBaseLevelUpXpValue();
        this.selectPoint = 5;
    }

    public void setPlayer_class(ServerPlayerEntity player, IPlayerClass player_class) {
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


    public void setMaxHealth(ServerPlayerEntity player,int amount) {
        if (amount <= 0 ) {
            throw new RuntimeException("value can't be under 0");
        }
        this.maxHealth = amount;
        syncPlayerHealth(player);
    }

    public void addMaxHealth(ServerPlayerEntity player, int amount) {
        this.maxHealth += amount;
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

    public float getCurrentMana() {
        return this.currentMana;
    }

    public void addCurrentMana(float val) {
        if (!isManaUser) {
            return;
        }
        this.currentMana += MathHelper.clamp(val, 0F, getMaxMana());
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
        this.maxMana += val;
        this.maxMana = MathHelper.clamp(this.maxMana, 0, Integer.MAX_VALUE);
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
    public void damageHealth(DamageSource s, PlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(this.currentHealth - amount, 0f, (float) getMaxHealth());
        float calculatedDamage = (amount / getMaxHealth()) * player.getMaxHealth();
        player.hurtTime = 0;
        player.damage(new QfCustomDamage(s, s.getSource(), amount), calculatedDamage);
    }
    public void syncPlayerHealth(ServerPlayerEntity player) {
        player.setHealth(getCurrentHealth() / getMaxHealth() * 20);
    }
}