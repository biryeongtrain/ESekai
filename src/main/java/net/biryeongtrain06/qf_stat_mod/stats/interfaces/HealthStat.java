package net.biryeongtrain06.qf_stat_mod.stats.interfaces;

import net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatEnums;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class HealthStat implements IStat{
    @Override
    public Number getStatValue(ItemStack stack) {
        ItemStatHandler handler = new ItemStatHandler(stack);
        int i = (int) handler.getItemStat(StatEnums.HEALTH_FLAT);
        return i;
    }

    @Override
    public Number getStatValue(LivingEntity entity) {
        return null;
    }

    @Override
    public Number getStatValue(ServerPlayerEntity player) {
        return null;
    }
}
