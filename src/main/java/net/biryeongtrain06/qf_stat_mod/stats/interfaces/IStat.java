package net.biryeongtrain06.qf_stat_mod.stats.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public interface IStat {
    Number getStatValue(ItemStack stack);
    default Number getStatValue(LivingEntity entity) {
        return null;
    }
    Number getStatValue(ServerPlayerEntity player);
}
