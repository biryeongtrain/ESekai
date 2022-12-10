package net.biryeongtrain06.qf_stat_mod.sidebar;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.playerdata.api.PlayerDataApi;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.DATA_STORAGE;

public class PlayerStatBar {
    public static void Open(ServerPlayerEntity player) {

        Sidebar sidebar = new Sidebar(Sidebar.Priority.LOWEST);

        sidebar.setTitle(Text.literal("Your Stat"));
        sidebar.setUpdateRate(1);

        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Player Class : " + PlayerDataApi.getCustomDataFor(p, DATA_STORAGE).getPlayer_class())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Level : " + PlayerDataApi.getCustomDataFor(p, DATA_STORAGE).getLevel())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("XP : " + PlayerDataApi.getCustomDataFor(p, DATA_STORAGE).getXP())));

        sidebar.addPlayer(player);
        sidebar.show();


    }

}
