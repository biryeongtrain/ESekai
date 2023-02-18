package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.utils.ElementHandler.ITEM_ELEMENT_KEY;
import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.LORE_KEY;
import static net.minecraft.nbt.NbtElement.LIST_TYPE;
import static net.minecraft.nbt.NbtElement.STRING_TYPE;

@Mixin(value = PacketByteBuf.class, priority = 700)
public class PacketByteBufMixin {

    @ModifyVariable(method = "writeItemStack", at = @At("HEAD"), argsOnly = true)
    private ItemStack qf$addLore(ItemStack stack) {
        if (stack.getNbt() == null || stack.getNbt().getString(ITEM_ELEMENT_KEY) == null) {
            return stack;
        }
        if (stack.getSubNbt(DISPLAY_KEY) != null) {
            try {
                final var list = Objects.requireNonNull(stack.getSubNbt(DISPLAY_KEY).getList(LORE_KEY, LIST_TYPE));
                removeAllElementLore(list);
            } catch (NullPointerException ignore) {}
        }
        return appendElementLore(stack.copy());
    }

    @Contract("_ -> param1")
    private @NotNull ItemStack appendElementLore(@NotNull ItemStack stack) {
        NbtCompound display = stack.getNbt().getCompound(DISPLAY_KEY);
        NbtList list = display.getList(LORE_KEY, STRING_TYPE);
        Elements element = Elements.getElementWithId(new Identifier(stack.getNbt().getString(ITEM_ELEMENT_KEY)));
        NbtString elementText = NbtString.of(Text.Serializer.toJson(element.getLoreText()));

        if (list.contains(elementText)) {
            list.add(elementText);
        }
        display.put(LORE_KEY, list);
        stack.getOrCreateNbt().put(DISPLAY_KEY, display);
        return stack;
    }

    private void removeAllElementLore(NbtList list) {
        Optional<Elements> OptionalElement = Arrays.stream(Elements.values()).parallel().filter(e -> list.contains(e.getLoreText())
        ).findFirst();
        Elements element = OptionalElement.orElse(null);
        if (element == null) {
            return;
        }
        list.remove(element.getLoreText());
    }
}
