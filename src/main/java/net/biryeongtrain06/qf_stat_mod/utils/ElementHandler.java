package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.server.network.ServerPlayerEntity;

public class ElementHandler {
    final static String ITEM_ELEMENT_KEY = "element";

    public static Elements getElement(ServerPlayerEntity player) {
        if (player.getMainHandStack().isEmpty()) {
            return Elements.PHYSICAL;
        }

    }
}
