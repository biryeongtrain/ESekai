package net.biryeongtrain06.qf_stat_mod.event;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.command.InitCommand;
import net.biryeongtrain06.qf_stat_mod.player.IServerPlayerEntity;
import net.biryeongtrain06.qf_stat_mod.player.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.sidebar.PlayerStatBar;
import net.biryeongtrain06.qf_stat_mod.utils.DamageUtils;
import net.biryeongtrain06.qf_stat_mod.utils.DataUtils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.DATA_STORAGE;
import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.debugLogger;


public class CallbackInit {
    public static void playerJoinCallback(ServerPlayerEntity player) {
        IServerPlayerEntity iPlayer = (IServerPlayerEntity) player;
        if (!iPlayer.isPlayedBefore()) {
            var PlayerStat = new PlayerStat();
            PlayerDataApi.setCustomDataFor(player, DATA_STORAGE, PlayerStat);
            PlayerStatBar.Open(player);
        }
    }

    public static void playerKilledCallback(PlayerEntity killer, LivingEntity victim) {
        ServerPlayerEntity killPlayer = (ServerPlayerEntity) killer;
        PlayerStat stat = PlayerDataApi.getCustomDataFor(killPlayer, DATA_STORAGE);
        stat.addXP(DataUtils.findXpModifier(victim));
        PlayerDataApi.setCustomDataFor(killPlayer, DATA_STORAGE, stat);
        debugLogger.debug("Killed!");
    }

    public static void EntityHitPlayerCallback(PlayerEntity player, LivingEntity entity, DamageSource source, float amount) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        serverPlayerEntity.sendMessage(Text.literal("Hooked!"));
        DamageUtils.PlayerDamageCalculate(player, source, amount);
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register(InitCommand::initCommand);
        PlayerJoinCallback.EVENT.register(CallbackInit::playerJoinCallback);
        PlayerKilledOtherCallback.EVENT.register(CallbackInit::playerKilledCallback);
        EntityHitPlayerCallback.EVENT.register(CallbackInit::EntityHitPlayerCallback);
    }
}
