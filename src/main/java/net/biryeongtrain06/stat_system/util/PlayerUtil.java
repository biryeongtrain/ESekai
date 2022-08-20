package net.biryeongtrain06.stat_system.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.Optional;

public class PlayerUtil {

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
}
