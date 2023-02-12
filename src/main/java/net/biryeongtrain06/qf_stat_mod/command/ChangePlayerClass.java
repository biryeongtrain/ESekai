package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.playerclass.WarriorPlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class ChangePlayerClass {
    public static int setCurrentHP(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerHelper.changePlayerClass(player, new WarriorPlayerClass());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
