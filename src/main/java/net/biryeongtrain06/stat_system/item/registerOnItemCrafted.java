package net.biryeongtrain06.stat_system.item;

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

    NbtCompound nbt = new NbtCompound();

    public registerOnItemCrafted(ItemStack item, PlayerEntity player) {
        this.item = item;
        this.player = player;
        this.level = setLevel(playerLevel);
        this.rarity = 5;
    }

    public registerOnItemCrafted(ItemStack item, PlayerEntity player, int level, int rarirty) {
        this.level = level;
        this.player = player;
        this.item = item;
        this.rarity = rarirty;
    }

    private int setLevel(int playerLevel) {
        return (int) Math.round(Math.random() * playerLevel);
    }
}
