package net.biryeongtrain06.qf_stat_mod.register;

import com.mojang.brigadier.CommandDispatcher;
import net.biryeongtrain06.qf_stat_mod.command.*;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class QfStatSystemCommands {
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
        dispatcher.register(
                literal("setCurrentHP")
                        .executes(HP::setCurrentHP)
        );
        dispatcher.register(
                literal("addCurrentHP")
                        .executes(HP::addCurrentHP)
        );
        dispatcher.register(
                literal("changePlayerClass")
                        .executes(ChangePlayerClass::setCurrentHP)
        );
        dispatcher.register(
                literal("getRarityModifier")
                        .executes(TestCommands::getRarity)
        );
        dispatcher.register(
                literal("openGUI")
                        .executes(TestCommands::openGUI)
        );
        dispatcher.register(
                literal("damageTest")
                        .executes(TestCommands::damageTest)
        );
        dispatcher.register(
                literal("damagePlayerTest")
                        .executes(TestCommands::selfDamage)
        );
        dispatcher.register(
                literal("getElement")
                        .executes(TestCommands::getElement)
        );
        dispatcher.register(
                literal("setElement")
                        .executes(TestCommands::setElement)
        );
        dispatcher.register(
                literal("getItemDamage")
                        .executes(TestCommands::getItemDamage)
        );
        dispatcher.register(
                literal("setItemDamage")
                        .executes(TestCommands::setItemDamage)
        );
        dispatcher.register(
                literal("getItemStat")
                        .executes(TestCommands::getItemStat)
        );
        SetItemStatCommand.register(dispatcher);
        SetItemStatCommandWithID.register(dispatcher);
    }
}
