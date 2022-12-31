package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.playerclass.IPlayerClass;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class PlayerHelper {

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
        PlayerStat playerStat = DataStorage.loadPlayerStat(player).getPlayer_class().onLostClass(player);
        Text debugPlayerClass = playerStat.getPlayer_class().getClassText();
        playerStat.setPlayer_class( playerClass);
        playerClass.onGetClass(player, playerStat);
        debugLogger.info("Player {}'s class is {} changed to {}", player.getPlayerListName(), debugPlayerClass , playerStat.getPlayer_class().getClassText());
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
        NbtCompound ownerTag = stack.getOrCreateSubNbt("SkullOwner");
        ownerTag.putUuid("Id", player.getUuid());
        return stack;
    }
}
