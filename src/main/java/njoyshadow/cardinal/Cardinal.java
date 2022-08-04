package njoyshadow.cardinal;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;
import net.minecraft.world.tick.Tick;
import njoyshadow.cardinal.component.TestComponent;

import static njoyshadow.cardinal.component.TestComponent.MAGIK;
import static njoyshadow.cardinal.component.TestComponent.useMagik;

public class Cardinal implements ModInitializer {
    public static String MOD_ID ="cardinal";

    @Override
    public void onInitialize() {
        AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {

                useMagik(player,entity);

            return ActionResult.PASS;
        }));

    }
}
