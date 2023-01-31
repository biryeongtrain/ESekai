package net.biryeongtrain06.qf_stat_mod.utils;

import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;
import static net.biryeongtrain06.qf_stat_mod.entity.EntityRank.*;

@SuppressWarnings("unused")
public class ExpHandler {
    private static int maxLevel = 100;
    private static HashMap<Identifier, Integer> xpModifier = new HashMap<>();
    private static float xpScaleModifier = 1.0f;
    private static float levelScaleModifier = 2.5f;
    private static float baseLevelUpXpValue = 100;
    private static float levelScaleAtHighLevelModifier = 3.0f;
    private static int amountSelectPointWhenLevelUp = 5;
    private static int scalingDistance = 250;


    public static void setXpModifier(JsonObject xp) {
        HashMap<Identifier, Integer> xpMap = new HashMap<>();
        xp.keySet().forEach(keyStr -> {
            String[] s = keyStr.split(":");
            xpMap.put(new Identifier(s[0], s[1]), xp.get(keyStr).getAsInt());
        });
        xpModifier = xpMap;
        debugLogger.info("xpModifier has Successfully Loaded!");
    }

    public static void initLevelModifier(JsonObject LevelData) {
        maxLevel = LevelData.get("maxLevel").getAsInt();
        baseLevelUpXpValue = LevelData.get("baseXpValue").getAsFloat();
        levelScaleModifier = LevelData.get("xpRequiresModifier").getAsFloat();
        levelScaleAtHighLevelModifier = LevelData.get("xpRequiresModifierAtHighLevel").getAsFloat();
        xpScaleModifier = LevelData.get("xpScaleModifier").getAsFloat();
        amountSelectPointWhenLevelUp = LevelData.get("amountSelectionPointWhenLevelUp").getAsInt();
        scalingDistance = LevelData.get("scalingDistance").getAsInt();
    }

    public static void setRaritySpawnChance(JsonObject rarityData) {
        RARE.setSpawn_chance(rarityData.get("rare_spawn_chance").getAsFloat());
        UNIQUE.setSpawn_chance(rarityData.get("unique_spawn_chance").getAsFloat());
        LEGENDARY.setSpawn_chance(rarityData.get("legendary_spawn_chance").getAsFloat());
        MYTHIC.setSpawn_chance(rarityData.get("mythic_spawn_chance").getAsFloat());
        COMMON.setSpawn_chance(RARE.getSpawn_chance() + UNIQUE.getSpawn_chance() + LEGENDARY.getSpawn_chance() + MYTHIC.getSpawn_chance());
    }

    public static float getXpScaleModifier() {
        return xpScaleModifier;
    }

    public static float getLevelScaleModifier() {
        return levelScaleModifier;
    }

    public static float getBaseLevelUpXpValue() {
        return baseLevelUpXpValue;
    }

    public static float getLevelScaleAtHighLevelModifier() {
        return levelScaleAtHighLevelModifier;
    }

    public static int getAmountSelectionPointWhenLevelUp() {
        return amountSelectPointWhenLevelUp;
    }

    public static int getAmountSelectPointWhenLevelUp() {
        return amountSelectPointWhenLevelUp;
    }

    public static int getScalingDistance() {
        return scalingDistance;
    }

    public static int getMaxLevel() {
        return maxLevel;
    }

    public static HashMap<Identifier, Integer> getXpModifier() {
        return xpModifier;
    }

    public static int findXpModifier(LivingEntity entity) {
        if (xpModifier.containsKey(EntityType.getId(entity.getType()))) {
            return xpModifier.get(EntityType.getId(entity.getType()));
        }
        return defaultXpModifier(entity);
    }

    private static int defaultXpModifier(LivingEntity entity) {
        if (entity instanceof EnderDragonEntity) {
            return 100;
        }
        if (entity instanceof WitherEntity) {
            return 100;
        }
        if (entity instanceof HostileEntity) {
            return 15;
        }
        if (entity instanceof Angerable) {
            return 10;
        }
        if (entity instanceof PathAwareEntity) {
            return 1;
        }
        return 0;
    }
}
