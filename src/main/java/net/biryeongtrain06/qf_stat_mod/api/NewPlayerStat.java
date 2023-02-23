package net.biryeongtrain06.qf_stat_mod.api;

import lombok.Getter;
import net.biryeongtrain06.qf_stat_mod.damage.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemDamageSources;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypeTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper.*;
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
    public boolean tryAddOrReplaceNumberInstance(ServerPlayerEntity player, StatTypes e, StatSubTag tag, Identifier id, float value) {
        IStats stat = instance.get(e);
        if (stat instanceof PercentStat && tag != PERCENT) return false;
        if (stat == null) return false;
        if (!stat.tryReplaceInstance(id, value, tag)) {
            stat.addStatInstance(id, value, tag);
        }
        if (e == HEALTH) calculateMaxHealth(player);
        if (e == MANA) calculateMaxMana();
        return true;
    }

    public boolean tryAddOrReplacePercentInstance(StatTypes e, Identifier id, float value) {
        IStats stat = instance.get(e);
        if (stat == null) return false;
        if (!(stat instanceof PercentStat)) return false;

        if(!stat.tryReplaceInstance(id, value, PERCENT)) {
            stat.addStatInstance(id, value, PERCENT);
        }
        return true;
    }

    public float getStatInstanceById(StatTypes e, Identifier id, StatSubTag tag) {
        IStats stat = instance.get(e);
        return stat.getInstanceById(id, tag);
    }

    public float getTotalStatValue(StatTypes e) {
        return instance.get(e).getTotalValue();
    }

    public boolean hasInstance(StatTypes e, Identifier id, StatSubTag tag) {
        return instance.get(e).hasInstance(id, tag);
    }

    public void calculateMaxMana() {
        boolean bl1 = this.currentMana == this.maxMana;
        int originalMana = this.maxMana;

        this.maxMana = Math.round(instance.get(MANA).getTotalValue());
        if (bl1 && maxMana - originalMana > 0) {
            this.currentMana = originalMana;
        }
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
        syncWithVanillaHealth(player);
    }
    public void syncWithVanillaHealth(ServerPlayerEntity player) {
        if (player.isDead()) {
            return;
        }
        player.setHealth(MathHelper.clamp((float) Math.floor(getCurrentHealth() / getMaxHealth() * 20), 0f, player.getMaxHealth()));
    }

    public void addStrengthInstance(ServerPlayerEntity player, Identifier id, float amount, StatSubTag tag) {
        this.tryAddOrReplaceNumberInstance(player, STRENGTH, tag, id, amount);
        int strength = Math.round(this.getTotalStatValue(STRENGTH));

        this.tryAddOrReplaceNumberInstance(player, HEALTH, FLAT, TextHelper.getId("strength_attribute"), 2 * strength);
        this.tryAddOrReplaceNumberInstance(player, BONUS_MELEE_DAMAGE, PERCENT ,TextHelper.getId("strength_attribute"), (float) (0.02 * strength));
    }

    public void addPerkInstance(ServerPlayerEntity player, StatTypes type, Identifier id, float amount, StatSubTag tag) {
        if (type.tag != StatTypeTag.SUB_STAT) return;
        this.tryAddOrReplaceNumberInstance(player, type, tag, id, amount);
        calculateSubStat(player, type);
    }

    private void calculateSubStat(ServerPlayerEntity player, StatTypes type) {
        int value = Math.round(this.getTotalStatValue(type));
        switch (type) {
            case STRENGTH -> {
                this.tryAddOrReplaceNumberInstance(player, HEALTH, FLAT, STRENGTH_MODIFIER_ID, 2 * value);
                this.tryAddOrReplaceNumberInstance(player, BONUS_MELEE_DAMAGE, PERCENT ,STRENGTH_MODIFIER_ID, (float) (0.02 * value));
            }
            case DEXTERITY -> {
                this.tryAddOrReplacePercentInstance(DODGE, DEXTERITY_MODIFIER_ID, (float) (0.05 * value));
                this.tryAddOrReplaceNumberInstance(player, BONUS_PROJECTILE_DAMAGE, PERCENT ,DEXTERITY_MODIFIER_ID, (float) 0.02 * value);
            }

            case CONSTITUTION -> {
                this.tryAddOrReplaceNumberInstance(player, HEALTH, FLAT, CONSTITUTION_MODIFIER_ID, (float) 3 * value);
                this.tryAddOrReplaceNumberInstance(player, ARMOR, FLAT, CONSTITUTION_MODIFIER_ID, (float) 5 * value);
                this.tryAddOrReplaceNumberInstance(player, ARMOR, PERCENT, CONSTITUTION_MODIFIER_ID, (float) 0.01 * value);
            }

            case WISDOM -> {
                this.tryAddOrReplaceNumberInstance(player, MANA, FLAT, WISDOM_MODIFIER_ID, (float) 5 * value);
                this.tryAddOrReplaceNumberInstance(player, EFFECTIVE_HEAL, FLAT, WISDOM_MODIFIER_ID, (float) 0.05 * value);
            }

            case INTELLIGENCE -> {
                this.tryAddOrReplaceNumberInstance(player, MANA, FLAT, INTELLIGENCE_MODIFIER_ID, (float) 3 * value);
                this.tryAddOrReplaceNumberInstance(player, BONUS_MAGIC_DAMAGE, PERCENT, INTELLIGENCE_MODIFIER_ID, (float) 0.02 * value);
            }

            case CHARISMA -> {

            }
        }

    }
    public void damageHealth(DamageSource s, ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(this.currentHealth - amount, 0f, (float) getMaxHealth());
        float calculatedDamage = (amount / getMaxHealth()) * player.getMaxHealth();
        player.hurtTime = 0;
        player.damage(s, calculatedDamage);
    }

    public void newDamageHealth(DamageSource s, Elements element, ServerPlayerEntity player, float amount) {
        float calculatedDamage = calculateDamageReduce(element, amount);
        IDamageSource iDamageSource = (IDamageSource) player.getDamageSources();
        QfDamageSource qfDamageSource = s.getSource().isPlayer() ? iDamageSource.getQfDamageSourceWithPlayerAttack(s, element, amount) :
                iDamageSource.getQfDamageSourceWithEntityAttack(s, element, amount);

        this.addCurrentHealth(player, -calculatedDamage);
        float vanillaDamage = (amount / this.maxHealth) * player.getMaxHealth();
        player.hurtTime = 0;

        player.damage(qfDamageSource, vanillaDamage);
    }

    private float calculateDamageReduce(Elements element, float amount) {
        var defensiveElement = element.getDefensiveStat();
        var value = instance.get(defensiveElement).getTotalValue();
        if (value == 0) return 0;
        if (defensiveElement == ARMOR) return value / (value + (amount * 2));
        return amount * (1 - value);
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
        instance.put(EFFECTIVE_HEAL, new FloatStat(0, 1, 1));
        instance.put(BONUS_XP, new PercentStat(0));
        instance.put(STRENGTH, new FloatStat(0, 1, 1));
        instance.put(DEXTERITY, new FloatStat(0,1,1));
        instance.put(CONSTITUTION, new FloatStat(0, 1, 1));
        instance.put(INTELLIGENCE, new FloatStat(0, 1,1));
        instance.put(WISDOM, new FloatStat(0,1,1));
        instance.put(CHARISMA, new FloatStat(0,1,1));

        calculateMaxHealth(player);
        this.currentHealth = getMaxHealth();
    }

}
