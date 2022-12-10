package net.biryeongtrain06.qf_stat_mod.player;


public class PlayerStat {
    private String player_class = "none";
    private int level = 1;
    private int xp = 0;

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
}
