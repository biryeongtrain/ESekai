package net.biryeongtrain06.qf_stat_mod.api;

import com.google.gson.annotations.JsonAdapter;
import lombok.Getter;
import net.biryeongtrain06.qf_stat_mod.MainStatSystem;
import net.biryeongtrain06.qf_stat_mod.damage.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.NonePlayerClass;
import net.biryeongtrain06.qf_stat_mod.stats.FloatStat;
import net.biryeongtrain06.qf_stat_mod.stats.PercentStat;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypeTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;

import static net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper.*;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.FLAT;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.PERCENT;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.*;

@SuppressWarnings("unused")
public class PlayerStat {

    @JsonAdapter(IStatGsonAdapter.class)
    private final EnumMap<StatTypes, IStats> instance;
    private Identifier playerClassId = new NonePlayerClass().getClassId();
    @Getter
    private int maxHealth = 100;
    @Getter
    private float currentHealth = 100;
    @Getter
    private int maxMana;
    @Getter
    private float currentMana;
    @Getter
    private int level = 1;
    @Getter
    private int xp = 0;
    private float needXpToLevelUp = ExpHandler.getBaseLevelUpXpValue();
    @SuppressWarnings("FieldMayBeFinal")
    @Getter
    private int usedSelectionPoint = 0;
    @Getter
    private int totalSelectionPoint = 5;

    public PlayerStat(ServerPlayerEntity player) {
        instance = MainStatSystem.ENTITY_INIT_STATS.get(new Identifier("minecraft:player")).clone();
        calculateMaxHealth(player);
        calculateMaxMana();
        this.currentMana = getMaxMana();
        this.currentHealth = getMaxHealth();
    }

    public void setPlayerClass(IPlayerClass player_class) {
        this.playerClassId = player_class.getClassId();

    }
    public Identifier getPlayerClassId() {
        return playerClassId;
    }

    public void setPlayerClassId(Identifier playerClassId) {
        this.playerClassId = playerClassId;
    }


    public void addXP(ServerPlayerEntity player, float i) {
        this.xp += i;

        if (xp >= needXpToLevelUp) {
            this.xp-= needXpToLevelUp;
            addLevel(player, 1);
            this.needXpToLevelUp = (float) (ExpHandler.getBaseLevelUpXpValue() * Math.pow(1 + ExpHandler.getLevelScaleModifier(), getLevel()));
        }
    }

    public void setLevel(int value) {
        this.level = value;
        this.totalSelectionPoint = value * 2 + 5;
        dumpNeedXpToLevelUp();
    }

    public void addLevel(ServerPlayerEntity player, int value) {
        this.level += value;
        this.totalSelectionPoint += value * 2;
        player.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.levelUp")).formatted(Formatting.GREEN));
        dumpNeedXpToLevelUp();
    }

    public float getNeedXpToLevelUp() {
        dumpNeedXpToLevelUp();
        return this.needXpToLevelUp;
    }

    private void dumpNeedXpToLevelUp() {
        this.needXpToLevelUp = (float) (ExpHandler.getBaseLevelUpXpValue() * Math.pow(1 + ExpHandler.getLevelScaleModifier(), getLevel()));
    }

    public boolean tryAddUnknownInstance(ServerPlayerEntity player, StatTypes type, StatSubTag tag, Identifier id, float value) {
        if (type.tag == StatTypeTag.SUB_STAT) return addPerkInstance(player, type, id, value, tag);
        if (instance.get(type) instanceof PercentStat) {
            return this.tryAddPercentInstance(type, id, value);
        }
        if (instance.get(type) instanceof FloatStat) {
            return this.tryAddNumberInstance(player, type, tag, id, value);
        }
        return false;
    }
    public boolean tryAddNumberInstance(ServerPlayerEntity player, StatTypes e, StatSubTag tag, Identifier id, float value) {
        IStats stat = instance.get(e);
        if (stat == null) return false;
        if (stat instanceof PercentStat && tag != PERCENT) return false;

        if (hasInstance(e, id, tag)) value += stat.getInstanceValueById(id, tag);
        stat.addStatInstance(id, value, tag);
        if (e == HEALTH) calculateMaxHealth(player);
        if (e == MANA) calculateMaxMana();
        return true;
    }

    public boolean tryReplaceNumberInstance(ServerPlayerEntity player, StatTypes e, StatSubTag tag, Identifier id, float value) {
        IStats stat = instance.get(e);
        if (stat == null) return false;
        if (stat instanceof PercentStat && tag != PERCENT) return false;
        stat.tryReplaceInstance(id, value, tag);
        return true;
    }

    public boolean tryAddPercentInstance(StatTypes e, Identifier id, float value) {
        IStats stat = instance.get(e);
        if (stat == null) return false;
        if (!(stat instanceof PercentStat)) return false;
        if(stat.hasInstance(id, PERCENT)) value += stat.getInstanceValueById(id, PERCENT);
        stat.addStatInstance(id, value, PERCENT);
        return true;
    }

    public boolean tryReplaceInstance(StatTypes type, Identifier id, float value) {
        IStats stat = instance.get(type);
        if (!(stat instanceof PercentStat)) return false;
        stat.tryReplaceInstance(id, value, PERCENT);
        return true;
    }

    public float getStatInstanceById(StatTypes e, Identifier id, StatSubTag tag) {
        IStats stat = instance.get(e);
        return stat.getInstanceValueById(id, tag);
    }

    public float getTotalStatValue(StatTypes e) {
        return instance.get(e).getTotalValue();
    }

    public boolean hasInstance(StatTypes e, Identifier id, StatSubTag tag) {
        return instance.get(e).hasInstance(id, tag);
    }

    public void tryRemoveInstance(ServerPlayerEntity player, StatTypes type, StatSubTag tag, Identifier id) {
        if(!hasInstance(type, id, tag)) return;
        instance.get(type).removeStatInstance(id, tag);
        if (type == HEALTH) calculateMaxHealth(player);
    }


    public void setCurrentMana(float value) {
        this.currentMana = MathHelper.clamp(value, 0, getMaxMana());
    }

    public void addCurrentMana(float value) {
        this.setCurrentMana(value + currentMana);
    }

    private void calculateMaxMana() {
        boolean bl1 = this.currentMana == this.maxMana;
        int originalMana = this.maxMana;

        this.maxMana = Math.round(instance.get(MANA).getTotalValue());
        if (bl1 && maxMana - originalMana > 0) {
            this.currentMana = originalMana;
        }
    }

    private void calculateMaxHealth(ServerPlayerEntity player) {
        int originalHealth = this.maxHealth;

        this.maxHealth = Math.round(instance.get(HEALTH).getTotalValue());
        if(maxHealth < 0) this.maxHealth = 1;
        int overDamagedRevisionValue = maxHealth - originalHealth;

        boolean isFullHealth = isOverThanFullHealth();
        if (isFullHealth && overDamagedRevisionValue < 0) {
            this.currentHealth = MathHelper.clamp(originalHealth, 1, maxHealth);
        }
        syncWithVanillaHealth(player);
    }

    private boolean isOverThanFullHealth() {
        return this.currentHealth >= this.maxHealth;
    }

    public void setCurrentHealth(ServerPlayerEntity player, float value) {
        this.currentHealth = MathHelper.clamp(value, 0, this.maxHealth);
        syncWithVanillaHealth(player);
    }

    public void addCurrentHealth(ServerPlayerEntity player, float value) {
        setCurrentHealth(player, this.currentHealth + value);
        syncWithVanillaHealth(player);
    }
    private void syncWithVanillaHealth(ServerPlayerEntity player) {
        if (player.isDead()) {
            return;
        }
        player.setHealth(MathHelper.clamp((float) Math.floor(getCurrentHealth() / getMaxHealth() * 20), 0f, player.getMaxHealth()));
    }

    public boolean tryAddPerkInstance(ServerPlayerEntity player, StatTypes type, Identifier id, float amount, StatSubTag tag) {
        if (!hasPerkPoint()) return false;
        this.usedSelectionPoint += 1;
        addPerkInstance(player, type, id, amount, tag);
        return true;
    }

    private boolean hasPerkPoint() {
        return this.usedSelectionPoint < this.totalSelectionPoint;
    }

    public boolean addPerkInstance(ServerPlayerEntity player, StatTypes type, Identifier id, float amount, StatSubTag tag) {
        if (type.tag != StatTypeTag.SUB_STAT) return false;
        this.tryAddNumberInstance(player, type, tag, id, amount);
        calculateSubStat(player, type);
        return true;
    }

    private void calculateSubStat(ServerPlayerEntity player, StatTypes type) {
        int value = Math.round(this.getTotalStatValue(type));
        switch (type) {
            case STRENGTH -> {
                this.tryAddNumberInstance(player, HEALTH, FLAT, STRENGTH_MODIFIER_ID, 2 * value);
                this.tryAddNumberInstance(player, BONUS_MELEE_DAMAGE, PERCENT ,STRENGTH_MODIFIER_ID, (float) (0.02 * value));
            }
            case DEXTERITY -> {
                this.tryAddPercentInstance(DODGE, DEXTERITY_MODIFIER_ID, (float) (0.05 * value));
                this.tryAddNumberInstance(player, BONUS_RANGED_DAMAGE, PERCENT ,DEXTERITY_MODIFIER_ID, (float) 0.02 * value);
            }

            case CONSTITUTION -> {
                this.tryAddNumberInstance(player, HEALTH, FLAT, CONSTITUTION_MODIFIER_ID, (float) 3 * value);
                this.tryAddNumberInstance(player, ARMOR, FLAT, CONSTITUTION_MODIFIER_ID, (float) 5 * value);
                this.tryAddNumberInstance(player, ARMOR, PERCENT, CONSTITUTION_MODIFIER_ID, (float) 0.01 * value);
            }

            case WISDOM -> {
                this.tryAddNumberInstance(player, MANA, FLAT, WISDOM_MODIFIER_ID, (float) 5 * value);
                this.tryAddNumberInstance(player, HEAL_EFFICIENT, FLAT, WISDOM_MODIFIER_ID, (float) 0.05 * value);
            }

            case INTELLIGENCE -> {
                this.tryAddNumberInstance(player, MANA, FLAT, INTELLIGENCE_MODIFIER_ID, (float) 3 * value);
                this.tryAddNumberInstance(player, BONUS_MAGIC_DAMAGE, PERCENT, INTELLIGENCE_MODIFIER_ID, (float) 0.02 * value);
            }

            case CHARISMA -> {

            }
        }

    }

    public void damageHealth(DamageSource s, Elements element, ServerPlayerEntity player, float amount) {
        float calculatedDamage = calculateDamageReduce(element, amount);
        IDamageSource iDamageSource = (IDamageSource) player.getDamageSources();
        QfDamageSource qfDamageSource = !s.isIn(DamageTypeTags.IS_PROJECTILE) ? iDamageSource.getQfDamageSourceWithMeleeAttack(s, element, amount) :
                iDamageSource.getQfDamageSourceWithProjectileAttack(s, element, amount);

        this.addCurrentHealth(player, -calculatedDamage);
        float vanillaDamage = (amount / this.maxHealth) * player.getMaxHealth();
        player.hurtTime = 0;

        player.damage(qfDamageSource, vanillaDamage);
    }

    public void applyEnvironmentDamage(DamageSource s, ServerPlayerEntity player, float amount) {
        IDamageSource iDamageSource = (IDamageSource) player.getDamageSources();
        QfDamageSource source = iDamageSource.getQfDamageSourceWithEnvironment(s, amount);
        float calculatedValue = ( (float) maxHealth / 20 )* amount;
        this.addCurrentHealth(player, -calculatedValue);
        player.damage(source, amount);
    }
    private float calculateDamageReduce(Elements element, float amount) {
        var defensiveElement = element.getDefensiveStat();
        var value = instance.get(defensiveElement).getTotalValue();
        if (value == 0) return amount;
        if (defensiveElement == ARMOR) return amount * (1 - (value / (value + (amount * 2))));
        return amount * (1 - value);
    }

    public void tick(ServerPlayerEntity player) {

        if(player.age % 20 == 0) {

            if (currentHealth < maxHealth) {
                addCurrentHealth(player, getTotalStatValue(REGEN_HEALTH_PER_SECOND));
            }
            if (currentMana < maxMana) {
                addCurrentMana(getTotalStatValue(REGEN_MANA_PER_SECOND));
            }
        }
    }

}
