package net.biryeongtrain06.stat_system.mixin;

import net.biryeongtrain06.stat_system.component.StatComponent;
import net.biryeongtrain06.stat_system.item.registerOnItemCrafted;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.biryeongtrain06.stat_system.MainStatSystem.debugLogger;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "onCraft", at = @At(value = "HEAD"))
    public void onCraft(World world, PlayerEntity player, int amount, CallbackInfo ci) {
        ItemStack item = (ItemStack) (Object) this;
        if (item.getItem() instanceof SwordItem) {
            debugLogger.info("Start Resister Item");
            new registerOnItemCrafted(item, player, registerOnItemCrafted.setLevel(StatComponent.PLAYERSTAT.get(player).getLevel()) ,registerOnItemCrafted.setRarity());
        }
    }
}
