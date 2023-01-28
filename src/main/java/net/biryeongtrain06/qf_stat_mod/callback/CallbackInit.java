package net.biryeongtrain06.qf_stat_mod.callback;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.command.InitCommand;
import net.biryeongtrain06.qf_stat_mod.entity.OnEntitySpawnSetting;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerStatBar;
import net.biryeongtrain06.qf_stat_mod.player.IServerPlayerEntity;
import net.biryeongtrain06.qf_stat_mod.utils.DamageHandler;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;


public class CallbackInit {
    private static void playerJoinCallback(ServerPlayerEntity player) {
        IServerPlayerEntity iPlayer = (IServerPlayerEntity) player;
        if (!iPlayer.isPlayedBefore() || DataStorage.loadPlayerStat(player) == null) {
            var PlayerStat = new PlayerStat(player);
            DataStorage.savePlayerStat(player, PlayerStat);
            iPlayer.setPlayedBefore(true);
        }
        PlayerStatBar.Open(player);
    }

    private static void playerKilledCallback(PlayerEntity killer, LivingEntity victim) {
        ServerPlayerEntity killPlayer = (ServerPlayerEntity) killer;
        IServerPlayerEntity iPlayer =(IServerPlayerEntity) killPlayer;
        PlayerStat stat = PlayerDataApi.getCustomDataFor(killPlayer, PLAYER_STAT_DATA_STORAGE);
        int xp = ExpHandler.findXpModifier(victim);
        stat.addXP((float) xp);
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

    public static void init() {
        CommandRegistrationCallback.EVENT.register(InitCommand::initCommand);
        PlayerJoinCallback.EVENT.register(CallbackInit::playerJoinCallback);
        PlayerKilledOtherCallback.EVENT.register(CallbackInit::playerKilledCallback);
        EntityHitPlayerCallback.EVENT.register(CallbackInit::entityHitPlayerCallback);
        MobSpawningCallback.EVENT.register(CallbackInit::onMobSpawned);
    }
}
