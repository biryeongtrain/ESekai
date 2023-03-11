package net.biryeongtrain06.qf_stat_mod.gui;

import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

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

        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Class : ")
               .append(PlayerHelper.getPlayerClass(DataStorage.loadPlayerStat(p).getPlayerClassId()).getClassText())));

        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Lv : " + DataStorage.loadPlayerStat(p).getLevel())));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("XP : " + DataStorage.loadPlayerStat(p).getXp())
                .append(Text.literal( " / " + (int)DataStorage.loadPlayerStat(p).getNeedXpToLevelUp()))));
        sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Hi " + p.getDisplayName())));

        return sidebar;
    }
}
