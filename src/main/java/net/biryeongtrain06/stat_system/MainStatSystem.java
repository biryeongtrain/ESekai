package net.biryeongtrain06.stat_system;

import net.biryeongtrain06.stat_system.testComponent.TestComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainStatSystem implements ModInitializer {
    public static String MOD_ID ="cardinal";
    final public static Logger debugLogger = LogManager.getLogger("Qf Stat Debug");

    @Override
    public void onInitialize() {
        AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {

                TestComponent.useMagik(player,entity);

            return ActionResult.PASS;
        }));

    }
}
