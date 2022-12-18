package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import utils.DataUtils;

import java.util.HashMap;

public class getModifier {
    public static int getModifier(CommandContext<ServerCommandSource> objectCommandContext) {
        ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
        HashMap<Identifier, Integer> hashMap = DataUtils.getXpModifier();
        hashMap.forEach((key, value) -> {
            player.sendMessage(Text.literal(key.toString() + " : " + value.toString()));
        });
        return 1;
    }
}
