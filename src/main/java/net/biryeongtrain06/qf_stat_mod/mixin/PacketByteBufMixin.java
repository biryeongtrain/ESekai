package net.biryeongtrain06.qf_stat_mod.mixin;

import io.netty.buffer.ByteBuf;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.Nbt2EnumMapAdapter;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.ArmorItem;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

import static net.biryeongtrain06.qf_stat_mod.item.ElementHandler.ITEM_ELEMENT_KEY;
import static net.biryeongtrain06.qf_stat_mod.item.ItemStatHandler.STAT_KEY;
import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.LORE_KEY;
import static net.minecraft.nbt.NbtElement.LIST_TYPE;
import static net.minecraft.nbt.NbtElement.STRING_TYPE;

@Mixin(value = PacketByteBuf.class, priority = 700)
public abstract class PacketByteBufMixin {

    @ModifyVariable(method = "writeItemStack", at = @At("HEAD"), argsOnly = true)
    private ItemStack qf$addLore(ItemStack stack) {
        if (stack.getNbt() == null || stack.getNbt().getString(ITEM_ELEMENT_KEY) == null || !stack.isDamageable() || stack.isStackable()) {
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
        NbtCompound display = stack.getOrCreateSubNbt(DISPLAY_KEY);
        NbtList list = display.getList(LORE_KEY, STRING_TYPE);
        if (list == null) list = new NbtList();
        if (!(stack.getItem() instanceof ArmorItem)) {
            Elements element = Elements.getElementWithId(new Identifier(stack.getNbt().getString(ITEM_ELEMENT_KEY)));
            NbtString elementText = NbtString.of(Text.Serializer.toJson(element.getLoreText()));

            if (!list.contains(elementText)) {
                list.add(elementText);
            }

            list.add(NbtString.of(Text.Serializer.toJson(Text.empty())));
        }
        list = setStatLoreV2(stack, list);
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

    private NbtList setStatList(ItemStack stack, NbtList list) {
        NbtCompound statRootCompound = stack.getNbt().getCompound(STAT_KEY);
        statRootCompound.getKeys().forEach(statType -> { // stat -> health 등등 -> flat / percent / -> id -> value
            StatTypes statEnum = StatTypes.getStatByName((statType));
            NbtCompound statCompound = statRootCompound.getCompound(statType);

            statCompound.getKeys().forEach(subtag -> {
                float value = statCompound.getFloat(subtag);
                if (!subtag.toLowerCase().equals("flat")) value *= 100;
                list.add(NbtString.of(Text.Serializer.toJson(Text.translatable(TextHelper.createTranslation(subtag.toLowerCase()) + "_tooltip", new Object[]{statEnum.getTranslatableName(), value}).formatted(statEnum.getFormat()))));
            });
        });
        return list;
    }

    private NbtList setStatLoreV2(ItemStack stack, NbtList list) {
        NbtCompound statRootCompound = stack.getSubNbt(STAT_KEY);
        EnumMap<StatTypes, IStats> instance = Nbt2EnumMapAdapter.ConvertNbtCompoundAsMap(statRootCompound);

        instance.forEach((stat, iStats) -> {
            list.addAll(iStats.getSeparatedStatLore());
        });

        return list;
    }
}
