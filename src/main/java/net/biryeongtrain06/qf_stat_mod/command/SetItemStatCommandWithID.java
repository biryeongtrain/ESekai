package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.biryeongtrain06.qf_stat_mod.api.ItemStats;
import net.biryeongtrain06.qf_stat_mod.command.suggest.StatSubTagSuggestionProvider;
import net.biryeongtrain06.qf_stat_mod.command.suggest.StatSuggestionProvider;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SetItemStatCommandWithID {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("setItemStatId")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(argument("stat", StringArgumentType.word()).suggests(new StatSuggestionProvider())
                        .then(argument("tag", StringArgumentType.word()).suggests(new StatSubTagSuggestionProvider())
                                .then(argument("id", IdentifierArgumentType.identifier())
                                        .then(argument("value", FloatArgumentType.floatArg())
                                                .executes(ctx -> onExecuted(ctx.getSource(), StringArgumentType.getString(ctx, "stat"), StringArgumentType.getString(ctx, "tag"), IdentifierArgumentType.getIdentifier(ctx, "id"), FloatArgumentType.getFloat(ctx, "value")))
                                        ))))
        );
    }

    private static int onExecuted(ServerCommandSource source, String type, String tag, Identifier id, float value) {
        try {
            ItemStack stack = source.getPlayer().getMainHandStack();
            StatTypes stat = StatTypes.getStatByName(type.toLowerCase());
            StatSubTag subTag = StatSubTag.getStatByName(tag.toUpperCase());

            if (stack.isEmpty())
                throw new SimpleCommandExceptionType(Text.translatable(TextHelper.createTranslation("command_exception_invalid_stack"))).create();
            if (stat == null)
                throw new SimpleCommandExceptionType(Text.translatable(TextHelper.createTranslation("command_exception_invalid_stat"))).create();
            if (subTag == null)
                throw new SimpleCommandExceptionType(Text.translatable(TextHelper.createTranslation("command_exception_invalid_subtag"))).create();

            boolean result = ItemStats.setItemStat(stack, stat, subTag, id, value);
            return result ? 1 : 0;
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
