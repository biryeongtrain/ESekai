package net.biryeongtrain06.qf_stat_mod.entity;

import net.biryeongtrain06.qf_stat_mod.command.GameRuleKeys;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public class OnEntitySpawnSetting {
    public static void onLoad(LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            return;
        }
        if (!(entity instanceof MobEntity)) {
            return;
        }
        setUpNewMobOnSpawn(entity);
    }

    private  static void setUpNewMobOnSpawn(LivingEntity entity) {
        int MAX_LEVEL = 100;
        int SCALING_DISTANCE = 400;
        PlayerEntity nearestPlayer = PlayerUtils.getNearestPlayer((ServerWorld) entity.world, (LivingEntity) entity);

        GameRules gameRules = entity.getWorld().getGameRules();

        if (gameRules.getBoolean(GameRuleKeys.ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING)) {
            if (nearestPlayer == null) {
                float hp = (float) (Math.random() * 200 + 50);
                EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                entityAttributeInstance.setBaseValue(hp);
                entity.setHealth(hp);
            }
            else {
                entity.setHealth((float) (Math.random() * (50 * 5) + 50));
                //TODO ADD HEALTH MODIFIER
            }
        }
    }
}
