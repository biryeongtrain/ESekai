package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.entity.EntityRank;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerMainGui;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class TestCommands {
    public static int actionbar(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            ServerPlayNetworkHandler playNetworkHandler = player.networkHandler;
            player.sendMessage(Text.literal("test"));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getRarity(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            player.sendMessage(Text.literal("Common : " + EntityRank.COMMON.getSpawn_chance()));
            player.sendMessage(Text.literal("Rare : " + EntityRank.RARE.getSpawn_chance()));
            player.sendMessage(Text.literal("Unique : " + EntityRank.UNIQUE.getSpawn_chance()));
            player.sendMessage(Text.literal("Legendary : " + EntityRank.LEGENDARY.getSpawn_chance()));
            player.sendMessage(Text.literal("Mythic : " + EntityRank.MYTHIC.getSpawn_chance()));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    public static int openGUI(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerMainGui playerMainGui = new PlayerMainGui(player);
            playerMainGui.open();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
