package net.biryeongtrain06.qf_stat_mod.util.enums;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public enum Rarity {
    Common("common", 1, Formatting.WHITE),
    UnCommon("uncommon", 2, Formatting.GREEN),
    Rare("rare", 3, Formatting.BLUE),
    Epic("epic", 4, Formatting.LIGHT_PURPLE),
    Legendary("legendary", 5, Formatting.GOLD);

    Rarity(String translationKey, int id, Formatting color) {
        this.translationKey = MOD_ID + ".rarity_" + translationKey;
        this.id = id;
        this.format = color;
    }
    public String translationKey;
    public int id;

    public Formatting format;

    public static Rarity getRarityEnum(int rarityNumber) {
        for (Rarity rarity : Rarity.values()) {
            if(rarity.id == rarityNumber) {
                return rarity;
            }
        }
        return null;
    }

    public Text getTranslationKey() {
        return Text.translatable(this.translationKey).append(Text.of(" ")).append(Text.translatable(MOD_ID + ".rarity.grade")).fillStyle(Style.EMPTY.withItalic(false)).formatted(this.format);
    }
}
