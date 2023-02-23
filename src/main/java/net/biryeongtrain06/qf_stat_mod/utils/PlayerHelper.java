package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.IPlayerClass;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.NonePlayerClass;
import net.biryeongtrain06.qf_stat_mod.player.playerclass.WarriorPlayerClass;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PlayerHelper {

    public final static Identifier STRENGTH_MODIFIER_ID = TextHelper.getId("strength_modifier");
    public final static Identifier DEXTERITY_MODIFIER_ID = TextHelper.getId("dexterity_modifier");
    public final static Identifier CONSTITUTION_MODIFIER_ID = TextHelper.getId("constitution_modifier");
    public final static Identifier INTELLIGENCE_MODIFIER_ID = TextHelper.getId("intelligence_modifier");
    public final static Identifier WISDOM_MODIFIER_ID = TextHelper.getId("wisdom_modifier");
    public final static Identifier CHARISMA_MODIFIER_ID = TextHelper.getId("charisma_modifier");
    private static final List<IPlayerClass> playerClassList = new ArrayList<>();
    public static ServerPlayerEntity getNearestPlayer (ServerWorld world, LivingEntity entity) {
        return getNearestPlayer(world, entity.getPos());
    }

    public static ServerPlayerEntity getNearestPlayer (ServerWorld world, BlockPos pos) {
        return getNearestPlayer(world, new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
    }

    public static ServerPlayerEntity getNearestPlayer (ServerWorld world, Vec3d pos) {
        Optional<ServerPlayerEntity> player = world.getPlayers()
                .stream()
                .min(Comparator.comparingDouble(x -> x.squaredDistanceTo(pos)));

        return player.orElse(null);
    }

    public static Formatting getPlayerHealthFormat(ServerPlayerEntity player) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        int healthPercent = (int) (playerStat.getCurrentHealth() / playerStat.getMaxHealth() * 100);
        if (healthPercent >= 80) return Formatting.GREEN;
        if (healthPercent >= 40) return Formatting.GOLD;
        if (healthPercent >= 20) return Formatting.RED;
        return Formatting.DARK_RED;
    }

    private static void register(IPlayerClass playerClass) {
        playerClassList.add(playerClass);
    }

    public static IPlayerClass getPlayerClass(Identifier i) {
        for (IPlayerClass iPlayerClass : playerClassList) {
            if (iPlayerClass.getClassId().equals(i)) {
                return iPlayerClass;
            }
        }
        return null;
    }
    static {
        register(new NonePlayerClass());
        register(new WarriorPlayerClass());
    }
}
