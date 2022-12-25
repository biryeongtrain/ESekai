package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public interface IPlayerClass {

    Text getClassText();
    Identifier getClassId();
    Formatting getTextFormat();
    String getClassTranslatable();
    void onGetClass(ServerPlayerEntity player, PlayerStat playerStat);
    PlayerStat onLostClass(ServerPlayerEntity player);

    void sendClassLostMessage(ServerPlayerEntity player);
    void sendClassGainMessage(ServerPlayerEntity player);
}
