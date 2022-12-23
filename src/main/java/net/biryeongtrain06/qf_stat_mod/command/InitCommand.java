package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class InitCommand {
    public static void initCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment env) {
        dispatcher.register(
                literal("getXp")
                        .executes(GetXp::getXP)
        );
        dispatcher.register(
                literal("getModifier")
                        .executes(GetModifier::getModifier)
        );
        dispatcher.register(
                literal("toggleSystemMessage")
                        .executes(DisplaySystemMessage::toggleSystemMessage)
        );
    }
}
