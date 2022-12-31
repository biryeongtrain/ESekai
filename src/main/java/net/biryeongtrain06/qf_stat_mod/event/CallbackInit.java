package net.biryeongtrain06.qf_stat_mod.event;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.command.InitCommand;
import net.biryeongtrain06.qf_stat_mod.player.IServerPlayerEntity;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerStatBar;
import net.biryeongtrain06.qf_stat_mod.utils.DamageHandler;
import net.biryeongtrain06.qf_stat_mod.utils.ExpHandler;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;


public class CallbackInit {
    public static void playerJoinCallback(ServerPlayerEntity player) {
        IServerPlayerEntity iPlayer = (IServerPlayerEntity) player;
        if (!iPlayer.isPlayedBefore() || PlayerDataApi.getCustomDataFor(player, PLAYER_STAT_DATA_STORAGE) == null) {
            var PlayerStat = new PlayerStat();
            PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, PlayerStat);
            iPlayer.setPlayedBefore(true);
        }
        PlayerStatBar.Open(player);
    }

    public static void playerKilledCallback(PlayerEntity killer, LivingEntity victim) {
        ServerPlayerEntity killPlayer = (ServerPlayerEntity) killer;
        IServerPlayerEntity iPlayer =(IServerPlayerEntity) killPlayer;
        PlayerStat stat = PlayerDataApi.getCustomDataFor(killPlayer, PLAYER_STAT_DATA_STORAGE);
        int xp = ExpHandler.findXpModifier(victim);
        stat.addXP(killPlayer, (float) xp);
        if (iPlayer.isDisplaySystemMessage()) {
            killPlayer.sendMessage(Text.translatable(TextHelper.createTranslation("system_message.killed"), victim.getDisplayName(), xp).formatted(Formatting.GREEN));
        }
        PlayerDataApi.setCustomDataFor(killPlayer, PLAYER_STAT_DATA_STORAGE, stat);
    }

    public static void EntityHitPlayerCallback(PlayerEntity player, LivingEntity entity, DamageSource source, float amount) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        DamageHandler.PlayerDamageCalculate(player, source, amount);
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register(InitCommand::initCommand);
        PlayerJoinCallback.EVENT.register(CallbackInit::playerJoinCallback);
        PlayerKilledOtherCallback.EVENT.register(CallbackInit::playerKilledCallback);
        EntityHitPlayerCallback.EVENT.register(CallbackInit::EntityHitPlayerCallback);
    }
}
