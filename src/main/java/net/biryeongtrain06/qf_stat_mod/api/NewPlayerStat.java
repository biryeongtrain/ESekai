package net.biryeongtrain06.qf_stat_mod.api;

import lombok.Getter;
import net.biryeongtrain06.qf_stat_mod.stats.NumberStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums.*;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.*;

public class NewPlayerStat {
    @Getter
    private int maxHealth = 100;
    @Getter
    private float currentHealth;
    private final EnumMap<StatEnums, IStats> instance = new EnumMap<>(StatEnums.class);

    public NewPlayerStat(ServerPlayerEntity player) {
        init(player);

    }
    public boolean tryAddInstance(ServerPlayerEntity player, StatEnums e, StatSubTag tag, Identifier id, Number value) {
        IStats stat = instance.get(e);
        float f = value.floatValue();

        if (stat == null) return false;
        if (!stat.tryReplaceInstance(id, value.floatValue(), tag)) {
            stat.addStatInstance(id, f, tag);
        }

        calculateMaxHealth(player);
        return true;
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

    public void init(ServerPlayerEntity player) {
        instance.put(HEALTH, new NumberStat());
        instance.put(MANA, new NumberStat());
        instance.put(ARMOR, new NumberStat());

        initStatInstance();

        calculateMaxHealth(player);
        this.currentHealth = getMaxHealth();
    }

    public void initStatInstance() {
        IStats healthInstance = instance.get(HEALTH);
        healthInstance.addStatInstance(TextHelper.getId("base_value"), 100, FLAT);
        healthInstance.addStatInstance(TextHelper.getId("base_value"), 1, PERCENT);
        healthInstance.addStatInstance(TextHelper.getId("base_value"), 1, MULTIPLIER);
        IStats manaInstance = instance.get(MANA);
        manaInstance.addStatInstance(TextHelper.getId("base_value"), 100, FLAT);
        manaInstance.addStatInstance(TextHelper.getId("base_value"), 1, PERCENT);
        manaInstance.addStatInstance(TextHelper.getId("base_value"), 1, MULTIPLIER);
        IStats armorInstance = instance.get(ARMOR);
        armorInstance.addStatInstance(TextHelper.getId("base_value"), 100, FLAT);
        armorInstance.addStatInstance(TextHelper.getId("base_value"), 1, PERCENT);
        armorInstance.addStatInstance(TextHelper.getId("base_value"), 1, MULTIPLIER);
    }
}
