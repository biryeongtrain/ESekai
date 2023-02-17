package net.biryeongtrain06.qf_stat_mod.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface OnCraftItemCallback {
    Event<OnCraftItemCallback> EVENT = EventFactory.createArrayBacked(OnCraftItemCallback.class, (listener) -> (itemStack, world, player) -> {
        for (OnCraftItemCallback handler : listener) {
            ActionResult result = handler.onCraft(itemStack, world, player);

            if (result == ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onCraft(ItemStack stack, World world, PlayerEntity player);
}
