package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.minecraft.server.network.ServerPlayerEntity;

public interface IPlayerClass {

    void onGetClass(ServerPlayerEntity player);
    void onLostClass(ServerPlayerEntity player);
}
