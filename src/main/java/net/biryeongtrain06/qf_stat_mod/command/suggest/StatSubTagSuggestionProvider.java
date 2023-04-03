package net.biryeongtrain06.qf_stat_mod.command.suggest;

import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatSubTagSuggestionProvider extends QfSuggestProviders{
    @Override
    List<String> getSuggestionList() {
        return Arrays.stream(StatSubTag.values()).map(x -> x.toString().toLowerCase()).collect(Collectors.toList());
    }
}
