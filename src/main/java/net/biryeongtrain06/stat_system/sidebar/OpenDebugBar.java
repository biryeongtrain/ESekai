package net.biryeongtrain06.stat_system.sidebar;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.stat_system.component.PlayerStatComponent.STAT;

public class OpenDebugBar {

    public static int OpenGUI(CommandContext<ServerCommandSource> objectCommandContext) {

        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            Sidebar sidebar = new Sidebar(Sidebar.Priority.OVERRIDE);

            sidebar.setTitle(Text.literal("Debug Sidebar"));
            sidebar.setUpdateRate(1);

            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Health : " + STAT.get(p).getHealth())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Defense : " + STAT.get(p).getDefense())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Dodge : "  + STAT.get(p).getDodge())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Mana : "  + STAT.get(p).getMana())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Xp : "  + STAT.get(p).getXp())));
            sidebar.addLines(SidebarLine.create(0, (p) -> Text.literal("Level : "  + STAT.get(p).getLevel())));

            sidebar.addPlayer(player);
            sidebar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
