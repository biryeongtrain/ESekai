package net.biryeongtrain06.stat_system.commands;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class gameRule {

        public static GameRules.Key<GameRules.BooleanRule> ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING;

        public static void setupGameRule() {
        ENTITY_FOLLOWS_PLAYER_LEVEL_SCALING = GameRuleRegistry.register("LevelScalingType", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    }
}
