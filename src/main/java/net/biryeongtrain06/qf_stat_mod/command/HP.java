package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class HP {
    public static int setCurrentHP(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerStat playerStat = DataStorage.loadPlayerStat(player);
            playerStat.setCurrentHealth(10);
            DataStorage.savePlayerStat(player,playerStat);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int addCurrentHP(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerStat playerStat = DataStorage.loadPlayerStat(player);
            playerStat.addCurrentHealth(10);
            DataStorage.savePlayerStat(player,playerStat);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
