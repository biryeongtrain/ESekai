package net.biryeongtrain06.qf_stat_mod.playerclass;

import net.biryeongtrain06.qf_stat_mod.utils.TextUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class NonePlayerClass extends BasicPlayerClass implements IPlayerClass{
    @Override
    public void onGetClass(ServerPlayerEntity player) {
        sendClassGainMessage(player);
    }

    @Override
    public void onLostClass(ServerPlayerEntity player) {
        sendClassGainMessage(player);
    }

    @Override
    public Text getClassText() {
        return Text.translatable(TextUtils.createTranslation("class.none.name"));
    }

    @Override
    public Identifier getClassId() {
        return new Identifier(MOD_ID, "class_none");
    }
}
