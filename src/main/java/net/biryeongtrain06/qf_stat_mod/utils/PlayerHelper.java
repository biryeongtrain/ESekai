package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.playerclass.NonePlayerClass;
import net.biryeongtrain06.qf_stat_mod.playerclass.WarriorPlayerClass;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class PlayerHelper {

    private static List<IPlayerClass> playerClassList = new ArrayList<>();
    public static PlayerEntity getNearestPlayer (ServerWorld world, LivingEntity entity) {
        return getNearestPlayer(world, entity.getPos());
    }

    public static PlayerEntity getNearestPlayer (ServerWorld world, BlockPos pos) {
        return getNearestPlayer(world, new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
    }

    public static PlayerEntity getNearestPlayer (ServerWorld world, Vec3d pos) {
        Optional<ServerPlayerEntity> player = world.getPlayers()
                .stream()
                .min(Comparator.comparingDouble(x -> x.squaredDistanceTo(pos)));

        return player.orElse(null);
    }

    public static void ChangePlayerClass(ServerPlayerEntity player, IPlayerClass playerClass) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        IPlayerClass originalPlayerClass = getPlayerClass(playerStat.getPlayerClassId());
        playerStat = originalPlayerClass.onLostClass(player);
        Text debugPlayerClass = originalPlayerClass.getClassText();
        playerStat.setPlayer_class(playerClass);
        playerClass.onGetClass(player, playerStat);
        debugLogger.info("Player {}'s class changed : {} -> {}", player.getDisplayName(), debugPlayerClass, playerClass.getClassText());
    }

    public static Formatting getPlayerHealthFormat(ServerPlayerEntity player) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        int healthPercent = (int) (playerStat.getCurrentHealth() / playerStat.getMaxHealth() * 100);
        if (healthPercent >= 80) return Formatting.GREEN;
        if (healthPercent >= 40) return Formatting.GOLD;
        if (healthPercent >= 20) return Formatting.RED;
        return Formatting.DARK_RED;
    }
    public static ItemStack getHead(ServerPlayerEntity player) {
        ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
        stack.setCustomName(player.getDisplayName());
        NbtCompound ownerTag = stack.getOrCreateSubNbt(SkullItem.SKULL_OWNER_KEY);
        ownerTag.putUuid("Id", player.getUuid());
        setHeadLore(stack, player);
        return stack;
    }

    private static void setHeadLore(ItemStack stack, ServerPlayerEntity player) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        NbtList lore = new NbtList();
        NbtCompound itemNBT = stack.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);
        lore.add(NbtString.of(Text.Serializer.toJson(Text.empty()
                .append(StatEnums.HEALTH.getTranslatableName())
                .append(Text.literal(" : " + playerStat.getMaxHealth())))
                .formatted(StatEnums.HEALTH.getFormat())));
        lore.add(NbtString.of(Text.Serializer.toJson(Text.empty()
                    .append(StatEnums.MANA.getTranslatableName())
                    .append(Text.literal(" : " + playerStat.getMaxMana()))
                    .formatted(StatEnums.MANA.getFormat()))));
        lore.add(NbtString.of(Text.Serializer.toJson(Text.empty()
                .append(StatEnums.ARMOR.getTranslatableName())
                .append(Text.literal(" : " + playerStat.getArmor()))
                .formatted(StatEnums.ARMOR.getFormat()))));
        lore.add(NbtString.of(Text.Serializer.toJson(Text.empty()
                .append(StatEnums.DODGE.getTranslatableName())
                .append(Text.literal(" : " + playerStat.getDodge()))
                .formatted(StatEnums.DODGE.getFormat()))));
        lore.add(NbtString.of(Text.Serializer.toJson(Text.empty()
                .append(StatEnums.SELECT_POINT.getTranslatableName())
                .append(Text.literal(" : " + playerStat.getSelectPoint()))
                .formatted(StatEnums.SELECT_POINT.getFormat()))));
        itemNBT.put(ItemStack.LORE_KEY, lore);
    }
    public static void register(IPlayerClass playerClass) {
        playerClassList.add(playerClass);
    }

    public static IPlayerClass getPlayerClass(Identifier i) {
        for (IPlayerClass iPlayerClass : playerClassList) {
            if (iPlayerClass.getClassId().equals(i)) {
                return iPlayerClass;
            }
        }
        return null;
    }
    static {
        register(new NonePlayerClass());
        register(new WarriorPlayerClass());
    }
}
