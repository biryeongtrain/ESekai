package net.biryeongtrain06.qf_stat_mod.mixin;

import com.google.common.collect.Multimap;
import net.biryeongtrain06.qf_stat_mod.component.StatComponent;
import net.biryeongtrain06.qf_stat_mod.item.registerOnItemCrafted;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "onCraft", at = @At(value = "HEAD"))
    public void onCraft(World world, PlayerEntity player, int amount, CallbackInfo ci) {
        ItemStack item = (ItemStack) (Object) this;
        if (item.getItem() instanceof SwordItem || item.getItem() instanceof ArmorItem) {
            new registerOnItemCrafted(item, player, registerOnItemCrafted.setLevel(StatComponent.PLAYERSTAT.get(player).getLevel()) ,registerOnItemCrafted.setRarity(), registerOnItemCrafted.setElement());
        }
    }

    @Inject(method = "getAttributeModifiers", locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getList(Ljava/lang/String;I)Lnet/minecraft/nbt/NbtList;"))
    private void combineModifiers(
            EquipmentSlot slot,
            CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir,
            Multimap<EntityAttribute, EntityAttributeModifier> multimap
    ) {
        multimap.putAll(getItem().getAttributeModifiers(slot));
    }

}
