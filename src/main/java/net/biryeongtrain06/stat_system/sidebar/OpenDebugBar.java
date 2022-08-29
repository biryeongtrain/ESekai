package net.biryeongtrain06.stat_system.sidebar;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.stat_system.component.StatComponent.PLAYERSTAT;

public class OpenDebugBar {

    public static int OpenGUI(CommandContext<ServerCommandSource> objectCommandContext) {

        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            Sidebar sidebar = new Sidebar(Sidebar.Priority.OVERRIDE);

            sidebar.setTitle(Text.literal("Debug Sidebar"));
            sidebar.setUpdateRate(1);

            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Xp : "  + PLAYERSTAT.get(p).getXp())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Level : "  + PLAYERSTAT.get(p).getLevel())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Stat Point : "  + PLAYERSTAT.get(p).getStatPoint())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Health : " + PLAYERSTAT.get(p).getMaxHealth())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Defense : " + PLAYERSTAT.get(p).getDefense())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Dodge : "  + PLAYERSTAT.get(p).getDodge())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Mana : "  + PLAYERSTAT.get(p).getMana())));

            sidebar.addPlayer(player);
            sidebar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
