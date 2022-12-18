package net.biryeongtrain06.qf_stat_mod.utils;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;

public class DataUtils {
    private static HashMap<Identifier, Integer> xpModifier = new HashMap<>();

    public static void setXpModifier(JsonObject xp) {
        HashMap<Identifier, Integer> xpMap = new HashMap<>();
        xp.keySet().forEach(keyStr -> {
            String[] s = keyStr.split(":");
            xpMap.put(new Identifier(s[0], s[1]), xp.get(keyStr).getAsInt());
        });
        xpModifier = xpMap;
        debugLogger.info("xpModifier has Successfully Loaded!");
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
