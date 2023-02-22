package net.biryeongtrain06.qf_stat_mod.api;

import lombok.Getter;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.*;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.*;

@SuppressWarnings("unused")
public class NewPlayerStat {
    @Getter
    private int maxHealth = 100;
    @Getter
    private float currentHealth;
    private int maxMana;
    private float currentMana;
    private final EnumMap<StatTypes, IStats> instance = new EnumMap<>(StatTypes.class);
    @SuppressWarnings("FieldMayBeFinal")
    private int selectionPoint = 5;

    public NewPlayerStat(ServerPlayerEntity player) {
        init(player);

    }
    public boolean tryAddNumberInstance(ServerPlayerEntity player, StatTypes e, StatSubTag tag, Identifier id, float value) {
        IStats stat = instance.get(e);
        if (stat instanceof PercentStat && tag != PERCENT) return false;
        if (stat == null) return false;
        if (!stat.tryReplaceInstance(id, value, tag)) {
            stat.addStatInstance(id, value, tag);
        }
        if (e == HEALTH) calculateMaxHealth(player);
        return true;
    }

    public boolean tryAddPercentInstance(StatTypes e, Identifier id, float value) {
        IStats stat = instance.get(e);
        if (stat == null) return false;
        if (!(stat instanceof PercentStat)) return false;

        if(!stat.tryReplaceInstance(id, value, PERCENT)) {
            stat.addStatInstance(id, value, PERCENT);
        }
        return true;
    }

    public float getStatValue(StatTypes e) {
        return instance.get(e).getTotalValue();
    }

    public boolean hasInstance(StatTypes e, Identifier id, StatSubTag tag) {
        return instance.get(e).hasInstance(id, tag);
    }

    public void calculateMaxHealth(ServerPlayerEntity player) {
        boolean isFullHealth = isFullHealth();
        int originalHealth = this.maxHealth;

        this.maxHealth = Math.round(instance.get(HEALTH).getTotalValue());
        int overDamagedRevisionValue = maxHealth - originalHealth;

        if (isFullHealth && overDamagedRevisionValue > 0) {
            this.currentHealth = originalHealth;
        }
        syncWithVanillaHealth(player);
    }

    public boolean isFullHealth() {
        return this.currentHealth == this.maxHealth;
    }

    public void setCurrentHealth(ServerPlayerEntity player, float value) {
        this.currentHealth = MathHelper.clamp(value, 0, this.maxHealth);
        syncWithVanillaHealth(player);
    }

    public void addCurrentHealth(ServerPlayerEntity player, float value) {
        setCurrentHealth(player, this.currentHealth + value);
    }

    public void syncWithVanillaHealth(ServerPlayerEntity player) {
        if (player.isDead()) {
            return;
        }
        player.setHealth(MathHelper.clamp((float) Math.floor(getCurrentHealth() / getMaxHealth() * 20), 0f, player.getMaxHealth()));
    }

    public void calculateMaxMana() {
        boolean bl1 = this.currentMana == this.maxMana;
        int originalMana = this.maxMana;

        this.maxMana = Math.round(instance.get(MANA).getTotalValue());
        if (bl1 && maxMana - originalMana > 0) {
            this.currentMana = originalMana;
        }
    }

    public void damageHealth(DamageSource s, ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(this.currentHealth - amount, 0f, (float) getMaxHealth());
        float calculatedDamage = (amount / getMaxHealth()) * player.getMaxHealth();
        player.hurtTime = 0;
        player.damage(s, calculatedDamage);
    }

    public void init(ServerPlayerEntity player) {
        instance.put(HEALTH, new FloatStat(100, 1, 1));
        instance.put(MANA, new FloatStat(100, 1, 1));
        instance.put(ARMOR, new FloatStat(0, 1, 1));
        instance.put(DODGE, new PercentStat(0));
        instance.put(FIRE_RESISTANCE, new PercentStat(0));
        instance.put(WATER_RESISTANCE, new PercentStat(0));
        instance.put(EARTH_RESISTANCE, new PercentStat(0));
        instance.put(LIGHT_RESISTANCE, new PercentStat(0));
        instance.put(DARK_RESISTANCE, new PercentStat(0));
        instance.put(BONUS_MELEE_DAMAGE, new FloatStat(0, 1, 1));
        instance.put(BONUS_PROJECTILE_DAMAGE, new FloatStat(0, 1, 1));
        instance.put(BONUS_XP, new PercentStat(0));

        calculateMaxHealth(player);
        this.currentHealth = getMaxHealth();
    }

}
