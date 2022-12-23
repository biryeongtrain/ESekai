package net.biryeongtrain06.qf_stat_mod.player;

public interface IServerPlayerEntity {
    boolean isPlayedBefore();

    void setPlayedBefore(boolean val);

    boolean isDisplaySystemMessage();
    void setDisplaySystemMessage(boolean val);
}
