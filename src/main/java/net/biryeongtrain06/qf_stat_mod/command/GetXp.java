package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GetXp {
    public static int getXP(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerStat playerStat = DataStorage.loadPlayerStat(player);
            player.sendMessage(Text.literal("XP : " + playerStat.getXp()));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
