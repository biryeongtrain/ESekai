package net.biryeongtrain06.qf_stat_mod.register;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class QfStatSystemGameRules {
    public static GameRules.Key<GameRules.BooleanRule> ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING;

    public static void setupGameRule() {
        ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING = GameRuleRegistry.register("EntityLevelScalingType", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    }
}
