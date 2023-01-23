package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.TestData;
import net.biryeongtrain06.qf_stat_mod.entity.EntityRank;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerMainGui;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerStatBar;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.TEST_PLAYER_DATA_STORAGE;

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

    public static int setTest(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            TestData test = PlayerDataApi.getCustomDataFor(player, TEST_PLAYER_DATA_STORAGE);
            test.setTest(1);
            PlayerDataApi.setCustomDataFor(player, TEST_PLAYER_DATA_STORAGE, test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
