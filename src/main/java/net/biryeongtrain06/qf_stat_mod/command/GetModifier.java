package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class GetModifier {
    public static int getModifier(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            HashMap<Identifier, Integer> hashMap = ExpHandler.getXpModifier();
            hashMap.forEach((key, value) -> player.sendMessage(Text.literal(key.toString() + " : " + value.toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
