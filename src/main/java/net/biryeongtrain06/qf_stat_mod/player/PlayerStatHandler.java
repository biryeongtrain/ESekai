package net.biryeongtrain06.qf_stat_mod.player;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.api.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;
import static net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler.STAT_KEY;

public class PlayerStatHandler {
    final ServerPlayerEntity player;
    PlayerStat playerStat;
    private final Identifier ITEM_MODIFIER_ID = TextHelper.getId("item_modifier");
    public PlayerStatHandler(ServerPlayerEntity player) {
        this.player = player;
        this.playerStat = DataStorage.loadPlayerStat(player);
    }


    public void changePlayerClass(IPlayerClass playerClass) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        IPlayerClass originalPlayerClass = PlayerHelper.getPlayerClass(playerStat.getPlayerClassId());
        playerStat = originalPlayerClass.onLostClass(player);
        Text debugPlayerClass = originalPlayerClass.getClassText();
        playerStat.setPlayerClass(playerClass);
        playerClass.onGetClass(player, playerStat);
        debugLogger.info("Player {}'s class changed : {} -> {}", player.getDisplayName(), debugPlayerClass, playerClass.getClassText());
    }

    public void syncItemStat(ItemStack oldStack, ItemStack newStack) {
        if (oldStack.getSubNbt(STAT_KEY) == null && newStack.getSubNbt(STAT_KEY) == null) return;
        if (oldStack.getSubNbt(STAT_KEY) == null || oldStack.getSubNbt(STAT_KEY).equals(newStack.getSubNbt(STAT_KEY))) return;
        removeItemStatToPlayer(oldStack);
        addItemStatsToPlayer(newStack);
    }

    private void removeItemStatToPlayer(ItemStack stack) {
        if (!isItemHasItemStack(stack)) {
            return;
        }
        if (stack.getSubNbt(STAT_KEY) == null) return;
        NbtCompound stats = stack.getSubNbt(STAT_KEY);
        stats.getKeys().stream().forEach(key ->
                stats.getCompound(key).getKeys().forEach(tag ->
                playerStat.tryAddUnknownInstance(player, StatTypes.getStatByName(key), StatSubTag.getStatByName(tag), ITEM_MODIFIER_ID, -stats.getCompound(key).getFloat(tag))));
        DataStorage.savePlayerStat(player, playerStat);
    }

    private void addItemStatsToPlayer(ItemStack stack) {
        if (!isItemHasItemStack(stack)) return;
        NbtCompound statCompound = stack.getSubNbt(STAT_KEY);

        if (statCompound == null) return;
        statCompound.getKeys().forEach(key ->
                statCompound.getCompound(key).getKeys().forEach(tag ->
                        playerStat.tryAddUnknownInstance(player, StatTypes.getStatByName(key), StatSubTag.getStatByName(tag), ITEM_MODIFIER_ID, statCompound.getCompound(key).getFloat(tag))));
        DataStorage.savePlayerStat(player, playerStat);
    }

    private boolean isItemHasItemStack(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return !stack.getSubNbt(STAT_KEY).isEmpty();
    }
}
