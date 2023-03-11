package net.biryeongtrain06.qf_stat_mod.register;

import net.biryeongtrain06.qf_stat_mod.MainStatSystem;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.ItemStats;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.callback.*;
import net.biryeongtrain06.qf_stat_mod.damage.DamageHandler;
import net.biryeongtrain06.qf_stat_mod.entity.OnEntitySpawnSetting;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerStatBar;
import net.biryeongtrain06.qf_stat_mod.interfaces.IServerPlayerEntityDuck;
import net.biryeongtrain06.qf_stat_mod.item.ElementHandler;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.ENTITY_MODIFIERS;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.DODGE;


public class QfStatSystemCallbacks {
    private static void playerJoinCallback(ServerPlayerEntity player) {
        IServerPlayerEntityDuck iPlayer = (IServerPlayerEntityDuck) player;
        if (!iPlayer.isPlayedBefore() || DataStorage.loadPlayerStat(player) == null) {
            PlayerStat playerStat = new PlayerStat(player);
            DataStorage.savePlayerStat(player, playerStat);
            iPlayer.setPlayedBefore(true);
        }
        PlayerStatBar.Open(player);
    }

    private static void playerKilledCallback(PlayerEntity killer, LivingEntity victim) {
        ServerPlayerEntity killPlayer = (ServerPlayerEntity) killer;
        IServerPlayerEntityDuck iPlayer =(IServerPlayerEntityDuck) killPlayer;
        PlayerStat stat = DataStorage.loadPlayerStat(killPlayer);
        int xp = ExpHandler.findXpModifier(victim);

        stat.addXP(killPlayer, (float) xp);
        if (iPlayer.isDisplaySystemMessage()) {
            killPlayer.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.killed"), victim.getDisplayName(), xp).formatted(Formatting.GREEN));
        }
        DataStorage.savePlayerStat(killPlayer, stat);
    }

    private static void entityHitPlayerCallback(ServerPlayerEntity player, LivingEntity entity, DamageSource source, float amount) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        if ((player == null && entity == null) || !source.getType().equals(DamageTypes.MOB_PROJECTILE) || !source.getType().equals(DamageTypes.MOB_ATTACK) || !source.getType().equals(DamageTypes.PLAYER_ATTACK) || !source.getType().equals(DamageTypes.PLAYER_EXPLOSION) ) {
            playerStat.applyEnvironmentDamage(source, player, amount);
            return;
        }
        float random = (float) (Math.random() * 100);
        if (random <= playerStat.getTotalStatValue(StatTypes.DODGE)) return;
        Elements element = null;
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player2 = (ServerPlayerEntity) entity;
            element = ItemStats.getPlayerItemElement(player);
        } else if (entity instanceof HostileEntity) {
            element = ENTITY_MODIFIERS.get(entity).getElement();
        }
        if (element == null) return;
        playerStat.damageHealth(source, element , player, amount);


        DataStorage.savePlayerStat(player, playerStat);
    }

    private static void onMobSpawned(LivingEntity entity, World world) {
        if (entity instanceof PlayerEntity) {
            return;
        }
        if (!(entity instanceof MobEntity)) {
            return;
        }
        OnEntitySpawnSetting.setUpNewMobOnSpawn(entity, world);
    }

    private static ActionResult onEntityDamagedByPlayer(@Nullable Entity attacker, Entity victim, DamageSource source, float amount) {
        if (!(victim instanceof HostileEntity) && !(victim instanceof LivingEntity)) {
            return ActionResult.PASS;
        }
        if (!(attacker instanceof ServerPlayerEntity)) {
            return ActionResult.PASS;
        }
        float dodge = ENTITY_MODIFIERS.get(victim).getTotalStatValue(DODGE);
        if ((Math.random() * 100) < dodge) {
            return ActionResult.PASS;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) attacker;
        ElementHandler elementHandler = new ElementHandler(player);
        Elements e = elementHandler.getElement();
        float resistance = ENTITY_MODIFIERS.get(victim).getTotalStatValue(e.getDefensiveStat());
        amount = Elements.calculateDamageReduce(e, resistance, amount);

        DamageHandler damageHandler = new DamageHandler(victim);
        damageHandler.DamageEntity(source, e, amount);

        return ActionResult.PASS;
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register(QfStatSystemCommands::initCommand);
        PlayerJoinCallback.EVENT.register(QfStatSystemCallbacks::playerJoinCallback);
        PlayerKilledOtherCallback.EVENT.register(QfStatSystemCallbacks::playerKilledCallback);
        PlayerHitByEntityCallback.EVENT.register(QfStatSystemCallbacks::entityHitPlayerCallback);
        MobSpawningCallback.EVENT.register(QfStatSystemCallbacks::onMobSpawned);
        EntityDamagedCallback.EVENT.register(QfStatSystemCallbacks::onEntityDamagedByPlayer);
    }
}
