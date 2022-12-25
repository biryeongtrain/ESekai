package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class NonePlayerClass extends BasicPlayerClass implements IPlayerClass{
    @Override
    public void onGetClass(ServerPlayerEntity player, PlayerStat playerStat) {
        sendClassGainMessage(player);
        DataStorage.savePlayerStat(player, playerStat);
    }

    @Override
    public PlayerStat onLostClass(ServerPlayerEntity player) {
        sendClassGainMessage(player);
        return DataStorage.loadPlayerStat(player);
    }

    @Override
    public Identifier getClassId() {
        return new Identifier(MOD_ID, "class_none");
    }

    @Override
    public Formatting getTextFormat() {
        return Formatting.WHITE;
    }

    @Override
    public String getClassTranslatable() {
        return TextHelper.createTranslation("class.none.name");
    }
}
