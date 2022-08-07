package net.biryeongtrain06.stat_system;

import net.biryeongtrain06.stat_system.testComponent.TestComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;

public class Cardinal implements ModInitializer {
    public static String MOD_ID ="cardinal";

    @Override
    public void onInitialize() {
        AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {

                TestComponent.useMagik(player,entity);

            return ActionResult.PASS;
        }));

    }
}
