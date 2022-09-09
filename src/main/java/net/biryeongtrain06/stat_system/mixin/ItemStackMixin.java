package net.biryeongtrain06.stat_system.mixin;

import net.biryeongtrain06.stat_system.component.StatComponent;
import net.biryeongtrain06.stat_system.item.registerOnItemCrafted;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "onCraft(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;I)V", at = @At(value = "HEAD"))
    public void onCraft(World world, PlayerEntity player, int amount, CallbackInfo info) {
        ItemStack item = (ItemStack) (Object) this;
        if (!item.isStackable()) {
            new registerOnItemCrafted(item, player, registerOnItemCrafted.setLevel(StatComponent.ENTITY_STAT.get(player).getLevel()) ,registerOnItemCrafted.setRarity());
        }
    }
}
