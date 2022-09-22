package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.util.enums.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static net.biryeongtrain06.qf_stat_mod.util.setItemStat.SetItemStatPerInstance.setLore;
import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.LORE_KEY;

@Mixin(value = PacketByteBuf.class, priority = 4000)
public class PacketByteBufMixin {
    @ModifyVariable(method = "writeItemStack", at = @At("HEAD"), argsOnly = true)
    private ItemStack addLore(ItemStack item) {
        if (!item.isEmpty() && item.getOrCreateNbt().getList("stat", 10).isEmpty()) {
            if(item.getOrCreateNbt().getBoolean("isNBTModified")) {
                return appendStatLore(item);
            }
        }
        return item;
    }

    @Contract("_-> pram1")
    private @NotNull ItemStack appendStatLore(@NotNull ItemStack item) {
        NbtCompound nbt = item.getOrCreateNbt();
        NbtCompound display = nbt.getCompound(DISPLAY_KEY);
        NbtList loreList = display.getList(LORE_KEY, NbtElement.STRING_TYPE);

        NbtString rarity = NbtString.of(Text.Serializer.toJson(Rarity.getRarityEnum(nbt.getInt("rarity")).getTranslationKey()));
        loreList.add(rarity);
        setLore(item, nbt.getInt("rarity"));

        return item;
    }
}
