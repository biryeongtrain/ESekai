package net.biryeongtrain06.qf_stat_mod.player;


public class PlayerStat {
    private String player_class = "none";
    private int level = 1;
    private int xp = 0;
    private int maxHealth = 100;
    private float currentHealth = 100;

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

    public String getPlayer_class() {
        return player_class;
    }

    public void setPlayer_class(String player_class) {
        this.player_class = player_class;
    }

    public void setMaxHealth(int amount) {
        this.maxHealth = amount;
    }

    public void setCurrentHealth(float amount) {
        this.currentHealth = amount;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }
    public void addCurrentHealth(float amount) {
        this.currentHealth += amount;
    }
    public float getCurrentHealth() {
        return this.currentHealth;
    }
}
