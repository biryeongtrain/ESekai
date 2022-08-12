package njoyshadow.cardinal.commands;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import njoyshadow.cardinal.component.PlayerStatComponent;

public class setDefense {
    public static int onExecuted(ServerPlayerEntity player, int value) {
        try {
            PlayerStatComponent.STAT.get(player).setDefense(value);
            player.sendMessage(Text.literal("Now your Defense is " + value));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
