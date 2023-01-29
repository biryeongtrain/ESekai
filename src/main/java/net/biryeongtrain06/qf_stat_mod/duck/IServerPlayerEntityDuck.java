package net.biryeongtrain06.qf_stat_mod.duck;

import net.minecraft.util.Identifier;

public interface IServerPlayerEntityDuck {
    boolean isPlayedBefore();

    void setPlayedBefore(boolean val);

    boolean isDisplaySystemMessage();
    void setDisplaySystemMessage(boolean val);

    Identifier getPlayerClass();
    void setPlayerClass(Identifier val);
}
