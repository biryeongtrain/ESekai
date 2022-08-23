package net.biryeongtrain06.stat_system.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.biryeongtrain06.stat_system.MainStatSystem;


@Config(name = MainStatSystem.MOD_ID)
public class StatSystemConfig implements ConfigData {

    int maxLevel = 100;

    int scalingDistance = 250;

    public int getMaxLevel() {
        return maxLevel != -1 ? maxLevel : Integer.MAX_VALUE;
    }

    public int getScalingDistance() {
        return scalingDistance <= 0 ? 250 : scalingDistance;
    }
}
