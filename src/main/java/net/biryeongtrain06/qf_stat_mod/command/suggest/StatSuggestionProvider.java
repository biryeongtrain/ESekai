package net.biryeongtrain06.qf_stat_mod.command.suggest;

import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatSuggestionProvider extends QfSuggestProviders{
    @Override
    List<String> getSuggestionList() {
        return Arrays.stream(StatTypes.values()).map(StatTypes::getName).collect(Collectors.toList());
    }
}
