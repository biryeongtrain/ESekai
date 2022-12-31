package net.biryeongtrain06.qf_stat_mod.gui;

import eu.pb4.sgui.api.gui.SimpleGui;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class PlayerMainGui extends SimpleGui {

    private final UUID playerHeadIndex;
    public PlayerMainGui(ServerPlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X1, player, false);
        this.playerHeadIndex = player.getUuid();
        this.setSlot(4, PlayerHelper.getHead(player));
    }

}
