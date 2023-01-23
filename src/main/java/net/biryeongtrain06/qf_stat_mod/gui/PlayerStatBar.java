package net.biryeongtrain06.qf_stat_mod.gui;

import eu.pb4.playerdata.api.PlayerDataApi;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;
import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.TEST_PLAYER_DATA_STORAGE;

public class PlayerStatBar {
    public static void Open(ServerPlayerEntity player) {
        Sidebar sidebar = getSidebar();

        sidebar.addPlayer(player);
        sidebar.show();
    }

    public static Sidebar getSidebar() {
        Sidebar sidebar = new Sidebar(Sidebar.Priority.LOWEST);
        sidebar.setTitle(Text.translatable(TextHelper.createTranslation("sidebar.title")));
        sidebar.setUpdateRate(1);

        //sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Class : ")
        //        .append(PlayerHelper.getPlayerClass(DataStorage.loadPlayerStat(p).getPlayerClassId()).getClassText())));

        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Class : ")
               .append(PlayerHelper.getPlayerClass(DataStorage.loadPlayerStat(p).getPlayerClassId()).getClassText())));

        /*sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("HP : ")
                .append(Text.literal((int) PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getCurrentHealth() + ""))
                .append(Text.literal(" / " + PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getMaxHealth()))
                .formatted(
                        PlayerHelper.getPlayerHealthFormat(p)
                )
        ));
        */
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Lv : " + PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getLevel())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("XP : " + (int)PlayerDataApi.getCustomDataFor(p, PLAYER_STAT_DATA_STORAGE).getXP())
                .append(Text.literal( " / " + (int)DataStorage.loadPlayerStat(p).getNeedXpToLevelUp()))));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("" +PlayerDataApi.getCustomDataFor(p, TEST_PLAYER_DATA_STORAGE).getTest())));
        return sidebar;
    }

}