package njoyshadow.cardinal;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.ActionResult;
import net.minecraft.world.tick.Tick;
import njoyshadow.cardinal.commands.setDefense;
import njoyshadow.cardinal.sidebar.OpenDebugBar;

import static net.minecraft.server.command.CommandManager.literal;


public class Cardinal implements ModInitializer {
    public static String MOD_ID ="cardinal";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("opendebugbar").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                            .executes(OpenDebugBar::OpenGUI)
            );
            dispatcher.register(
                    literal("setdefense").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                            .then(CommandManager.argument("player", EntityArgumentType.player()))
                            .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                    .executes(ctx -> setDefense.onExecuted(EntityArgumentType.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "value"))))

            );
        });

    }
}
