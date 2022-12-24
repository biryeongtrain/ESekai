package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.biryeongtrain06.qf_stat_mod.utils.TextUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public abstract class BasicPlayerClass implements IPlayerClass{

    public void sendClassGainMessage(ServerPlayerEntity player) {
        player.sendMessage(getClassText().copy().append(Text.translatable(TextUtils.createTranslation("gain_class"))).formatted(Formatting.GREEN));
    }

    public void sendClassLostMessage(ServerPlayerEntity player) {
        player.sendMessage(getClassText().copy().append(Text.translatable(TextUtils.createTranslation("lost_class"))).formatted(Formatting.RED));
    }
}
