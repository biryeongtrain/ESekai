package net.biryeongtrain06.qf_stat_mod.player;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.EnumMap;

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
        playerStat.setPlayer_class(playerClass);
        playerClass.onGetClass(player, playerStat);
        debugLogger.info("Player {}'s class changed : {} -> {}", player.getDisplayName(), debugPlayerClass, playerClass.getClassText());
    }

    public void syncItemStat(ItemStack oldStack, ItemStack newStack) {
        removeItemStatToPlayer(oldStack);
        addItemStatsToPlayer(newStack);
    }

    private void removeItemStatToPlayer(ItemStack stack) {
        if (!checkIfItemHasStack(stack)) {
            return;
        }
        if (stack.getSubNbt(STAT_KEY) == null) return;
        NbtCompound stats = stack.getSubNbt(STAT_KEY);
        stats.getKeys().stream().forEach(key ->
                stats.getCompound(key).getKeys().stream().forEach(tag ->
                playerStat.tryRemoveInstance(player, StatTypes.getStatByName(key), StatSubTag.getStatByName(tag), ITEM_MODIFIER_ID)));
        DataStorage.savePlayerStat(player, playerStat);
    }

    private void addItemStatsToPlayer(ItemStack stack) {
        if (!checkIfItemHasStack(stack)) return;
        NbtCompound statCompound = stack.getSubNbt(STAT_KEY);

        if (statCompound == null) return;
        statCompound.getKeys().stream().forEach(key ->
                statCompound.getCompound(key).getKeys().stream().forEach(tag ->
                        playerStat.tryAddUnknownInstance(player, StatTypes.getStatByName(key), StatSubTag.getStatByName(tag), ITEM_MODIFIER_ID, statCompound.getCompound(key).getFloat(tag))));
        DataStorage.savePlayerStat(player, playerStat);
    }

    private boolean checkIfItemHasStack(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return !stack.getOrCreateSubNbt(STAT_KEY).isEmpty();
    }

    private EnumMap<StatTypes, Number> getStatValue(NbtCompound stats, EnumMap<StatTypes, Number> map, boolean isRemoving) {
        int i = isRemoving ? -1 : 1;
        stats.getKeys().stream().parallel().forEach(key -> {
            StatTypes s = StatTypes.getStatByName(key);
            int type = stats.getType(key);
            Number n = 0;
            if (type == 5) {
                n = stats.getFloat(key);
            } else if (type == 3) {
                n = stats.getInt(key);
            }
            if (s == null) return;
            map.put(s, map.get(s).floatValue() + (n.floatValue()) * i);
        });
        return map;
    }
}
