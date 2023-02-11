package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.interfaces.IServerPlayerEntityDuck;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class DisplaySystemMessage {
    public static int toggleSystemMessage(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            IServerPlayerEntityDuck iPlayer = (IServerPlayerEntityDuck) player;
            if (iPlayer.isDisplaySystemMessage()) {
                iPlayer.setDisplaySystemMessage(false);
                player.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.disabled")));
                return 1;
            }
            iPlayer.setDisplaySystemMessage(true);
            player.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.enabled")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
