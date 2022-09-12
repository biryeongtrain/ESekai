package net.biryeongtrain06.stat_system.sidebar;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TestGui {

    public static int OpenGui(CommandContext<ServerCommandSource> objectCommandContext) {

        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            SimpleGui gui = new SimpleGui(ScreenHandlerType.GENERIC_3X3, player, false);

            gui.setSlot(0, new GuiElementBuilder()
                    .setItem(Items.TNT)
                    .glow()
                    .addLoreLine(Text.literal("test").formatted(Formatting.AQUA))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
