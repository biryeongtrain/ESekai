package net.biryeongtrain06.stat_system.commands;

import net.biryeongtrain06.stat_system.component.StatComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class setLevel {
    public static int onExecuted(ServerPlayerEntity player, int value) {
        try {
            StatComponent.PLAYERSTAT.get(player).setLevel(value);
            player.sendMessage(Text.literal("Now your Level is " + value));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
