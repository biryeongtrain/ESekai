package net.biryeongtrain06.stat_system.entity;

import net.biryeongtrain06.stat_system.MainStatSystem;
import net.biryeongtrain06.stat_system.commands.gameRule;
import net.biryeongtrain06.stat_system.component.StatComponent;
import net.biryeongtrain06.stat_system.util.PlayerUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
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
        setUpNewMobOnSpawn(entity);
    }

    public static void setUpNewMobOnSpawn(Entity entity) {
        if (entity.world.isClient) {
            throw new RuntimeException("Why this running in Client Side?");
        }
        int MAX_LEVEL = 50;
        int SCALING_DISTANCE = 500;
        PlayerEntity nearestPlayer = PlayerUtil.getNearestPlayer((ServerWorld) entity.world, (LivingEntity) entity);

        GameRules gameRules = entity.getWorld().getGameRules();
        if (gameRules.getBoolean(gameRule.ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING)) {
            if (nearestPlayer == null) {
                StatComponent.ENTITY_STAT.get(entity).setLevel((int) Math.round(Math.random() * 5));
                setName(entity);
            }
            else {
                StatComponent.ENTITY_STAT.get(entity).setLevel(StatComponent.PLAYERSTAT.get(nearestPlayer).getLevel());
                setName(entity);
            }
        }
        else {
            double distance = entity.world.getSpawnPos().getManhattanDistance(entity.getBlockPos());
            // TODO : MAKE LEVEL SYSTEM PER DISTANCE
            int level = MathHelper.clamp((int)distance / SCALING_DISTANCE, 0, MAX_LEVEL);
            StatComponent.ENTITY_STAT.get(entity).setLevel(level);
            setName(entity);
        }

    }

    private static void setName(Entity entity) {
        MutableText name = Text.literal(StatComponent.ENTITY_STAT.get(entity).getLevel() + "레벨").formatted(Formatting.BOLD).append(entity.getDisplayName()).formatted(Formatting.AQUA);
        entity.setCustomName(name);
    }
}
