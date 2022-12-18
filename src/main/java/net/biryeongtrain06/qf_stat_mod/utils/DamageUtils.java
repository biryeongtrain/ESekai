package utils;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.player.PlayerStat;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.DATA_STORAGE;

public class DamageUtils {
    public static void PlayerDamageCalculate(PlayerEntity player, DamageSource source, float amount) {
        if (!player.isInvulnerableTo(source)) {
            ServerPlayerEntity sPlayer = (ServerPlayerEntity) player;
            PlayerStat stat = PlayerDataApi.getCustomDataFor(sPlayer, DATA_STORAGE);
            stat.addCurrentHealth(-amount);
            PlayerDataApi.setCustomDataFor(sPlayer, DATA_STORAGE, stat);
            player.setHealth(stat.getCurrentHealth() / stat.getMaxHealth() * 20);
        }
    }
}
