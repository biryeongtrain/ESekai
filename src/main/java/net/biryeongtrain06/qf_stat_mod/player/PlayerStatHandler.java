package net.biryeongtrain06.qf_stat_mod.player;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;
import static net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler.STAT_KEY;

public class PlayerStatHandler {
    final ServerPlayerEntity player;
    PlayerStat playerStat;
    public PlayerStatHandler(ServerPlayerEntity player) {
        this.player = player;
        this.playerStat = DataStorage.loadPlayerStat(player);
    }


    public void changePlayerClass(IPlayerClass playerClass) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        IPlayerClass originalPlayerClass = PlayerHelper.getPlayerClass(playerStat.getPlayerClassId());
        playerStat = originalPlayerClass.onLostClass(player);
        Text debugPlayerClass = originalPlayerClass.getClassText();
        playerStat.setPlayer_class(playerClass);
        playerClass.onGetClass(player, playerStat);
        debugLogger.info("Player {}'s class changed : {} -> {}", player.getDisplayName(), debugPlayerClass, playerClass.getClassText());
    }

    public void syncItemStat(ItemStack stack1, ItemStack stack2) {

    }

    private void removeItemStatToPlayer(ItemStack stack) {
        NbtCompound stats = stack.getOrCreateSubNbt(STAT_KEY);
        if(stats.isEmpty()) {
            return;
        }
        HashMap<StatEnums, Number> map = playerStat.getMap();
    }
}
