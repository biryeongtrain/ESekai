package net.biryeongtrain06.stat_system.item;

import net.biryeongtrain06.stat_system.util.Elements;
import net.biryeongtrain06.stat_system.util.setItemStat.SetItemStatPerInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

import static net.biryeongtrain06.stat_system.MainStatSystem.debugLogger;
import static net.biryeongtrain06.stat_system.component.StatComponent.PLAYERSTAT;

public class registerOnItemCrafted {

    private final ItemStack item;
    private PlayerEntity player;
    private final int playerLevel;
    private int level = 0;

    private int rarity = 0;

    public registerOnItemCrafted(ItemStack item, PlayerEntity player) {
        this.item = item;
        this.player = player;
        this.playerLevel = PLAYERSTAT.get(this.player).getLevel();
        this.level = setLevel(playerLevel);
        this.rarity = 5;
        SetItemStatPerInstance.RegisterItemStatPerInstance(item, level, rarity);
    }

    public registerOnItemCrafted(ItemStack item, PlayerEntity player, int level, int rarity, Elements element) {
        this.player = player;
        this.playerLevel = PLAYERSTAT.get(this.player).getLevel();
        this.level = level;
        this.item = item;
        this.rarity = rarity;
        SetItemStatPerInstance.RegisterItemStatPerInstance(item, level, rarity, element);
    }

    public static int setLevel(int playerLevel) {
        int level = (int) Math.round(Math.random() * playerLevel);
        return level == 0 ? 1 : level;
    }

    public static int setRarity() {
        int value = (int) Math.ceil(Math.random() * 100);
        if (value <= 35) {
            return 1;
        }
        if (value <= 61) {
            return 2;
        }
        if (value <= 81) {
            return 3;
        }
        if (value <= 90) {
            return 4;
        }
        if (value >= 90) {
            return 5;
        }
        return 0;
    }

    public static Elements setElement() {
        int roll = (int) (Math.random() * 100);
        if (roll <= 50) {
            return Elements.Physical;
        }
        if (roll <= 62) {
            return Elements.Fire;
        }
        if (roll <= 74) {
            return Elements.Water;
        }
        if (roll <= 86) {
            return Elements.Earth;
        }
        if (roll <= 93) {
            return Elements.Light;
        }
        if (roll >= 93) {
            return Elements.Dark;
        }
        return Elements.Physical;
    }
}
