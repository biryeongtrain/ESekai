package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.playerclass.WarriorPlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;

public class changePlayerClass {
    public static int setCurrentHP(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerHelper.ChangePlayerClass(player, new WarriorPlayerClass());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
