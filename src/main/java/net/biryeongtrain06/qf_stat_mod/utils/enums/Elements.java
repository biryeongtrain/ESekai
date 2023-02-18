package net.biryeongtrain06.qf_stat_mod.utils.enums;

import com.google.gson.JsonObject;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mutable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums.*;

public enum Elements {
    PHYSICAL("element_physical", ARMOR, 20, Formatting.GOLD, "\u2726", false),
    FIRE("element_fire", FIRE_RESISTANCE, 20, Formatting.RED, "\u2600", true),
    WATER("element_water", WATER_RESISTANCE, 20, Formatting.AQUA, "\u2749", true),
    EARTH("element_earth", EARTH_RESISTANCE, 20, Formatting.GREEN, "\u273F", true),
    LIGHT("element_light", LIGHT_RESISTANCE, 10, Formatting.LIGHT_PURPLE, "\u269C", true),
    DARK("element_dark", DARK_RESISTANCE, 10, Formatting.DARK_PURPLE, "\u2726", true);

    final Text translatableName;
    final Identifier i;
    final StatEnums defensiveStat;
    final int spawnPercent;
    Formatting format;
    String icon;
    boolean isElement;

    Elements(String i, StatEnums defensiveStat, int spawnPercent, Formatting format, String icon, boolean isElement) {
        this.i = TextHelper.getId(i);
        this.translatableName = Text.translatable(MOD_ID+"."+i);
        this.defensiveStat = defensiveStat;
        this.spawnPercent = spawnPercent;
        this.format = format;
        this.icon = icon;
        this.isElement = isElement;
    }

    public static HashMap<Elements, Integer> getPercent() {
        HashMap<Elements, Integer> h = new HashMap<>();
        for(Elements e : values()) {
            h.put(e, e.spawnPercent);
        }
        return h;
    }

    public Text getTranslatableName() {
        return translatableName;
    }

    public Identifier getId() {
        return i;
    }

    public StatEnums getDefensiveStat() {
        return defensiveStat;
    }

    public int getSpawnPercent() {
        return spawnPercent;
    }

    public static HashMap<Elements, Integer> setPeakElement(Elements e) {
        HashMap<Elements, Integer> h = getPercent();
        for(Elements el : values()) {
            h.replace(el, el.spawnPercent / 2);
        }
        if (e == LIGHT || e == DARK) h.replace(e, 55);
        else h.replace(e, 60);
        return h;
    }

    public Formatting getFormat() {
        return format;
    }

    public String getIcon() {
        return icon;
    }

    public Text toHoverTextWithIcon() {
        MutableText text = (Text.empty().append(this.icon).formatted(this.format));
        text.formatted(this.format).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.empty().append(this.getTranslatableName()).formatted(this.format))));
        return text;
    }

    public Text getLoreText() {
        MutableText text = Text.empty().append(Text.translatable(TextHelper.createTranslation("element_lore")));
        text.append(" : ").append(this.icon + " ").append(this.translatableName).formatted(this.format);
        return text;
    }

    public static Elements getElementWithId(Identifier id) {
        Optional<Elements> element = Arrays.stream(values()).parallel()
                .filter(e -> e.getId().equals(id)).findFirst();
        return element.orElse(PHYSICAL);
    }

    public static float calculateDamageReduce(Elements e, int resistance, float amount) {
        if (e.isElement) {
            return (1 - resistance) * amount;
        }
        return amount * (1 - MathHelper.clamp(resistance / resistance + (2 * amount), -10f, 0.9f));
    }
}
