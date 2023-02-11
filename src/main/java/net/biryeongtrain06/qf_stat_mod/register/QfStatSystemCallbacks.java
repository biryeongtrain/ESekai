package net.biryeongtrain06.qf_stat_mod.register;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.callback.EntityHitPlayerCallback;
import net.biryeongtrain06.qf_stat_mod.callback.MobSpawningCallback;
import net.biryeongtrain06.qf_stat_mod.callback.PlayerJoinCallback;
import net.biryeongtrain06.qf_stat_mod.callback.PlayerKilledOtherCallback;
import net.biryeongtrain06.qf_stat_mod.interfaces.IServerPlayerEntityDuck;
import net.biryeongtrain06.qf_stat_mod.entity.OnEntitySpawnSetting;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerStatBar;
import net.biryeongtrain06.qf_stat_mod.utils.DamageHandler;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;


public class QfStatSystemCallbacks {
    private static void playerJoinCallback(ServerPlayerEntity player) {
        IServerPlayerEntityDuck iPlayer = (IServerPlayerEntityDuck) player;
        if (!iPlayer.isPlayedBefore() || DataStorage.loadPlayerStat(player) == null) {
            var PlayerStat = new PlayerStat();
            DataStorage.savePlayerStat(player, PlayerStat);
            iPlayer.setPlayedBefore(true);
        }
        PlayerStatBar.Open(player);
    }

    private static void playerKilledCallback(PlayerEntity killer, LivingEntity victim) {
        ServerPlayerEntity killPlayer = (ServerPlayerEntity) killer;
        IServerPlayerEntityDuck iPlayer =(IServerPlayerEntityDuck) killPlayer;
        PlayerStat stat = PlayerDataApi.getCustomDataFor(killPlayer, PLAYER_STAT_DATA_STORAGE);
        int xp = ExpHandler.findXpModifier(victim);
        stat.addXP(killPlayer, (float) xp);
        if (iPlayer.isDisplaySystemMessage()) {
            killPlayer.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.killed"), victim.getDisplayName(), xp).formatted(Formatting.GREEN));
        }
        PlayerDataApi.setCustomDataFor(killPlayer, PLAYER_STAT_DATA_STORAGE, stat);
    }

    private static void entityHitPlayerCallback(PlayerEntity player, LivingEntity entity, DamageSource source, float amount) {
        DamageHandler dmgHandler = new DamageHandler();
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        dmgHandler.PlayerDamageCalculate(player, source, amount);
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

    private static ActionResult onEntityDamaged(LivingEntity attacker, LivingEntity victim, DamageSource source, float amount) {

        return ActionResult.PASS;
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register(QfStatSystemCommands::initCommand);
        PlayerJoinCallback.EVENT.register(QfStatSystemCallbacks::playerJoinCallback);
        PlayerKilledOtherCallback.EVENT.register(QfStatSystemCallbacks::playerKilledCallback);
        EntityHitPlayerCallback.EVENT.register(QfStatSystemCallbacks::entityHitPlayerCallback);
        MobSpawningCallback.EVENT.register(QfStatSystemCallbacks::onMobSpawned);
    }
}
