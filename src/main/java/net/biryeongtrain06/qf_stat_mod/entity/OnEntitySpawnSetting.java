package net.biryeongtrain06.qf_stat_mod.entity;

import net.biryeongtrain06.qf_stat_mod.register.QfStatSystemGameRules;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class OnEntitySpawnSetting {

    public static void setUpNewMobOnSpawn(LivingEntity entity, World world) {
        if (world.isClient()) {
            return;
        }
        int MAX_LEVEL = 100;
        final float SCALING_DISTANCE = ExpHandler.getLevelScaleModifier();
        ServerPlayerEntity nearestPlayer = PlayerHelper.getNearestPlayer((ServerWorld) entity.world, entity);

        boolean gameRules = entity.getWorld().getGameRules().getBoolean(QfStatSystemGameRules.ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING);

    }
}
