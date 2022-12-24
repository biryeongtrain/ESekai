package net.biryeongtrain06.qf_stat_mod.api;


import net.biryeongtrain06.qf_stat_mod.playerclass.BasicPlayerClass;
import net.biryeongtrain06.qf_stat_mod.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.playerclass.NonePlayerClass;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class PlayerStat {
    private IPlayerClass player_class = new NonePlayerClass();
    private int level = 1;
    private int xp = 0;
    private int maxHealth = 100;
    private float currentHealth = 100;

    @NotNull
    public void addXP(int i) {
        this.xp += i;
    }

    public void setXP(int i) {
        this.xp = i;
    }

    public int getXP() {
        return this.xp;
    }

    public int getLevel() {
        return this.level;
    }

    public void addLevel(int i) {
        this.level += i;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public IPlayerClass getPlayer_class() {
        return player_class;
    }

    public void setPlayer_class(ServerPlayerEntity player, IPlayerClass player_class) {
        player_class.sendClassLostMessage(player);
        player_class.onGetClass(player);
        this.player_class = player_class;
        player_class.sendClassGainMessage(player);
        player_class.onLostClass(player);
    }

    public void setMaxHealth(int amount) {
        this.maxHealth = amount;
    }

    public void addMaxHealth(int amount) {
        this.maxHealth += amount;
    }

    public void setCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth = MathHelper.clamp(amount, 0, getMaxHealth());
        player.setHealth(getCurrentHealth() / getMaxHealth() * 20);
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }
    public void addCurrentHealth(ServerPlayerEntity player, float amount) {
        this.currentHealth += amount;
        player.setHealth(getCurrentHealth() / getMaxHealth() * 20);
    }
    public float getCurrentHealth() {
        return this.currentHealth;
    }
}
