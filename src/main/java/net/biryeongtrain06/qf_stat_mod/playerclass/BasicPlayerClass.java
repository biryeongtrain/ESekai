package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class BasicPlayerClass implements IPlayerClass{

    public void sendClassGainMessage(ServerPlayerEntity player) {
        player.sendMessage(getClassText().copy().append(Text.translatable(TextHelper.createTranslation("system_message.gain_class"))).formatted(Formatting.GREEN));
    }

    public void sendClassLostMessage(ServerPlayerEntity player) {
        player.sendMessage(getClassText().copy().append(Text.translatable(TextHelper.createTranslation("system_message.lost_class"))).formatted(Formatting.RED));
    }

    public Text getClassText() {
        return Text.translatable(getClassTranslatable()).formatted(getTextFormat());
    }
}
