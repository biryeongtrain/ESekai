package net.biryeongtrain06.stat_system.entity;

import net.biryeongtrain06.stat_system.commands.gameRule;
import net.biryeongtrain06.stat_system.component.StatComponent;
import net.biryeongtrain06.stat_system.stat.EntityStatSystem;
import net.biryeongtrain06.stat_system.util.PlayerUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public class onMobSpawn implements ServerEntityEvents.Load{
    @Override
    public void onLoad(Entity entity, ServerWorld world) {
        if (entity instanceof PlayerEntity) {
            return;
        }
        if (!(entity instanceof HostileEntity)) {
            return;
        }

    }

    public static void setUpNewMobOnSpawn(HostileEntity entity) {
        if (entity.world.isClient) {
            throw new RuntimeException("Why this running in Client Side?");
        }
        PlayerEntity nearestPlayer = null;
        nearestPlayer = PlayerUtil.getNearestPlayer((ServerWorld) entity.world, entity);

        GameRules gameRules = entity.getWorld().getGameRules();
        if (gameRules.getBoolean(gameRule.ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING)) {
            if (nearestPlayer == null) {
                StatComponent.ENTITY_STAT.get(entity).setLevel((int) Math.round(Math.random() * 5));
            }
        }

    }

}
