package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class WarriorPlayerClass extends BasicPlayerClass {
    @Override
    public Identifier getClassId() {
        return new Identifier(MOD_ID, "class_warrior");
    }

    @Override
    public Formatting getTextFormat() {
        return Formatting.RED;
    }

    @Override
    public String getClassTranslatable() {
        return TextHelper.createTranslation("class.warrior.name");
    }

    @Override
    public void onGetClass(ServerPlayerEntity player, PlayerStat playerStat) {
        playerStat.addMaxHealth(player, 10);
        playerStat.setArmor(playerStat.getArmor() + 10);
        DataStorage.savePlayerStat(player, playerStat);
        sendClassGainMessage(player);
    }

    @Override
    public PlayerStat onLostClass(ServerPlayerEntity player) {
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        playerStat.addMaxHealth(player, -10);
        playerStat.setArmor(playerStat.getArmor() - 10);
        sendClassLostMessage(player);
        return playerStat;
    }
}
