package net.biryeongtrain06.qf_stat_mod.api;


import net.biryeongtrain06.qf_stat_mod.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.playerclass.NonePlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class PlayerStat {
    private IPlayerClass player_class = new NonePlayerClass();
    private int level = 1;
    private float xp = 0;
    private int maxHealth = 100;
    private float currentHealth = 100;

    private float needXpToLevelUp;
    private int selectPoint = 5;

    @NotNull
    public void addXP(ServerPlayerEntity player, float i) {
        this.xp += i;
        this.needXpToLevelUp = (float) (PlayerExpHandler.getBaseLevelUpXpValue() * Math.pow(1 + PlayerExpHandler.getLevelScaleModifier(), getLevel()));
        if (xp >= needXpToLevelUp) {
            this.xp-= needXpToLevelUp;
            addLevel(player, 1);
        }
    }

    public float getNeedXpToLevelUp() {
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
        addSelectPoint(PlayerExpHandler.getAmountSelectionPointWhenLevelUp());
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public IPlayerClass getPlayer_class() {
        return player_class;
    }

    public void setPlayer_class(ServerPlayerEntity player, IPlayerClass player_class) {
        this.player_class = player_class;
    }

    public void setMaxHealth(int amount) {
        this.maxHealth = amount;
    }

    public void addMaxHealth(int amount) {
        this.maxHealth += amount;
    }

    public void setCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(amount, 0f, (float) getMaxHealth());
        player.setHealth(getCurrentHealth() / getMaxHealth() * 20);
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }
    public void addCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth += amount;
        this.currentHealth = MathHelper.clamp(this.currentHealth, 0f, (float) getMaxHealth());
        player.setHealth(getCurrentHealth() / getMaxHealth() * 20);
    }
    public float getCurrentHealth() {
        return this.currentHealth;
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
}
