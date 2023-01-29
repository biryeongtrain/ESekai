package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;

public class HP {
    public static int setCurrentHP(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerStat playerStat = DataStorage.loadPlayerStat(player);
            playerStat.setCurrentHealth(player,10);
            PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, playerStat);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int addCurrentHP(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerStat playerStat = DataStorage.loadPlayerStat(player);
            playerStat.addCurrentHealth(player, 10);
            PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, playerStat);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
