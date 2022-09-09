package net.biryeongtrain06.stat_system.item;

import net.biryeongtrain06.stat_system.util.setItemStat.SetItemStatPerInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

import static net.biryeongtrain06.stat_system.component.StatComponent.PLAYERSTAT;

public class registerOnItemCrafted {

    private final ItemStack item;
    private PlayerEntity player;
    private int playerLevel = PLAYERSTAT.get(this.player).getLevel();
    private int level = 0;
    private int rarity = 0;


    public registerOnItemCrafted(ItemStack item, PlayerEntity player) {
        this.item = item;
        this.player = player;
        this.level = setLevel(playerLevel);
        this.rarity = 5;
        new SetItemStatPerInstance(item, level, rarity);
    }

    public registerOnItemCrafted(ItemStack item, PlayerEntity player, int level, int rarity) {
        this.level = level;
        this.player = player;
        this.item = item;
        this.rarity = rarity;
        new SetItemStatPerInstance(item, level, rarity);
    }

    public static int setLevel(int playerLevel) {
        return (int) Math.round(Math.random() * playerLevel);
    }

    public static int setRarity() {
        int value = (int) Math.ceil(Math.random() * 100);
        if (value <= 35) {
            return 0;
        }
        if (value <= 61) {
            return 1;
        }
        if (value <= 81) {
            return 2;
        }
        if (value <= 90) {
            return 3;
        }
        if (value >= 90) {
            return 4;
        }
        return 0;
    }
}
