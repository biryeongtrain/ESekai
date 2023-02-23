package net.biryeongtrain06.qf_stat_mod.utils.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatSubTag {
    FLAT, PERCENT, MULTIPLIER;

    public static StatSubTag getStatByName(String s) {
        Optional<StatSubTag> optionalStatEnums = Arrays.stream(values()).filter(tag -> tag.name().equals(s)).findFirst();
        return optionalStatEnums.orElse(null);
    }
}
