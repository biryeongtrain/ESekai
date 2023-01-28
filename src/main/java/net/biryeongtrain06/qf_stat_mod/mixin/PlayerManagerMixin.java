package net.biryeongtrain06.qf_stat_mod.mixin;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.callback.PlayerJoinCallback;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;


@Mixin(value = PlayerManager.class, priority = 499)
public class PlayerManagerMixin {

    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    public void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerJoinCallback.EVENT.invoker().join(player);
    }

    @Inject(at = @At("TAIL"), method = "respawnPlayer")
    public void initPlayerStat(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        PlayerStat playerStat = PlayerDataApi.getCustomDataFor(player, PLAYER_STAT_DATA_STORAGE);
        playerStat.setCurrentHealth(playerStat.getMaxHealth());
        PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, playerStat);
    }
}
