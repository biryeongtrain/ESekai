package net.biryeongtrain06.qf_stat_mod.command.suggest;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class QfSuggestProviders implements SuggestionProvider<ServerCommandSource>{
    abstract List<String> getSuggestionList();

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        CommandSource.suggestMatching(this.getSuggestionList(), builder);
        return builder.buildFuture();
    }
}
