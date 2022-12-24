package net.biryeongtrain06.qf_stat_mod.sidebar;

import eu.pb4.playerdata.api.PlayerDataApi;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.biryeongtrain06.qf_stat_mod.player.IServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;

public class PlayerStatBar {
    public static void Open(ServerPlayerEntity player) {
        Sidebar sidebar = getSidebar();

        sidebar.addPlayer(player);
        sidebar.show();
    }

    public static Sidebar getSidebar() {
        Sidebar sidebar = new Sidebar(Sidebar.Priority.LOWEST);
        sidebar.setTitle(Text.literal("Your Stat"));
        sidebar.setUpdateRate(1);
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Player Class : ").append(PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getPlayer_class().getClassText())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("HP : " + PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getCurrentHealth() + " / " + PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getMaxHealth())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Level : " + PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getLevel())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("XP : " + PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getXP())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Enabled System Message : " + (((IServerPlayerEntity) p).isDisplaySystemMessage() ? "True" : "False"))));

        return sidebar;
    }

}
