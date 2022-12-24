package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface IPlayerClass {

    Text getClassText();
    Identifier getClassId();
    void onGetClass(ServerPlayerEntity player);
    void onLostClass(ServerPlayerEntity player);

    void sendClassLostMessage(ServerPlayerEntity player);
    void sendClassGainMessage(ServerPlayerEntity player);
}
