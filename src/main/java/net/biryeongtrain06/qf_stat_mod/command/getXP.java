package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.player.PlayerStat;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.DATA_STORAGE;

public class getXP {
    public static int getXP(CommandContext<ServerCommandSource> objectCommandContext) {
        ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
        PlayerStat playerStat = DATA_STORAGE.load(player);
        player.sendMessage(Text.literal("XP : " + playerStat.getXP()));

        return 1;
    }
}
