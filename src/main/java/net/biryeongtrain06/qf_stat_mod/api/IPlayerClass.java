package net.biryeongtrain06.qf_stat_mod.api;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/**
 * 플레이어 직업의 기반이 되는 인터페이스입니다.
 * @see BasicPlayerClass
 */
public interface IPlayerClass {

    default Identifier getModifierId() {return TextHelper.getId("class_modifier");}

    Text getClassText();
    /**
     * 플레이어 직업의 {@link Identifier}를 반환합니다.
     * @return {@link Identifier} 직업의 Identifier
     */
    Identifier getClassId();
    Formatting getTextFormat();
    String getClassTranslatable();
    void onGetClass(ServerPlayerEntity player, PlayerStat playerStat);
    PlayerStat onLostClass(ServerPlayerEntity player);

    void sendClassLostMessage(ServerPlayerEntity player);
    void sendClassGainMessage(ServerPlayerEntity player);
}
