package net.biryeongtrain06.qf_stat_mod.api;

import lombok.Getter;
import net.biryeongtrain06.qf_stat_mod.stats.HealthFlat;
import net.biryeongtrain06.qf_stat_mod.stats.HealthMulti;
import net.biryeongtrain06.qf_stat_mod.stats.HealthPercent;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums.*;

public class NewPlayerStat {
    @Getter
    private int maxHealth = 100;
    @Getter
    private float currentHealth;
    private final EnumMap<StatEnums, IStats> instance = new EnumMap<>(StatEnums.class);

    public NewPlayerStat(ServerPlayerEntity player) {
        init(player);
    }
    public boolean tryAddInstance(ServerPlayerEntity player, StatEnums e, Identifier id, Number value) {
        IStats stat = instance.get(e);
        float f = value.floatValue();

        if (stat == null) return false;
        if (!stat.tryModifyInstance(id, value.floatValue())) {
            stat.addStatInstance(id, f);
        }

        calculateMaxHealth(player);
        return true;
    }

    public void calculateMaxHealth(ServerPlayerEntity player) {
        boolean isFullHealth = isFullHealth();
        int originalHealth = this.maxHealth;

        this.maxHealth = Math.round(getHealthFlatValue() * getHealthPercentValue() * getHealthMultiValue());
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
        float val = MathHelper.clamp(value, 0, this.maxHealth);
        this.currentHealth = val;
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

    public int getHealthFlatValue() {
        return Math.round(instance.get(HEALTH_FLAT).getTotalValue());
    }
    public float getHealthPercentValue() {
        return instance.get(HEALTH_INCREASE_PERCENT).getTotalValue();
    }

    public float getHealthMultiValue() {
        return instance.get(HEALTH_INCREASE_MULTI).getTotalValue();
    }
    public void init(ServerPlayerEntity player) {
        instance.put(HEALTH_FLAT, new HealthFlat());
        instance.put(HEALTH_INCREASE_PERCENT, new HealthPercent());
        instance.put(HEALTH_INCREASE_MULTI, new HealthMulti());

        instance.get(HEALTH_FLAT).addStatInstance(TextHelper.getId("base_value"), 100);

        calculateMaxHealth(player);
        this.currentHealth = getMaxHealth();
    }
}
