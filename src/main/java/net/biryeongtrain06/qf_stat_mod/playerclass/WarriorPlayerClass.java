package net.biryeongtrain06.qf_stat_mod.playerclass;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.utils.TextUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;
import static net.biryeongtrain06.qf_stat_mod.api.DataStorage.PLAYER_STAT_DATA_STORAGE;

public class WarriorPlayerClass extends BasicPlayerClass {
    @Override
    public Text getClassText() {
        return Text.translatable(TextUtils.createTranslation("class.warrior.name"));
    }

    @Override
    public Identifier getClassId() {
        return new Identifier(MOD_ID, "class_warrior");
    }

    @Override
    public void onGetClass(ServerPlayerEntity player) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        playerStat.addMaxHealth(10);
        PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, playerStat);
        sendClassGainMessage(player);
    }

    @Override
    public void onLostClass(ServerPlayerEntity player) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        playerStat.addMaxHealth(-10);
        PlayerDataApi.setCustomDataFor(player, PLAYER_STAT_DATA_STORAGE, playerStat);
        sendClassLostMessage(player);
    }
}
