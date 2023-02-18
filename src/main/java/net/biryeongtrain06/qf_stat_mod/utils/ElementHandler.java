package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ElementHandler {
    public final static String ITEM_ELEMENT_KEY = "element";
    private final ItemStack ITEM_STACK;
    private final ServerPlayerEntity player;

    public ElementHandler(ItemStack stack, ServerPlayerEntity player) {
        this.ITEM_STACK = stack;
        this.player = player;
    }

    public ElementHandler(ServerPlayerEntity player) {
        this.ITEM_STACK = player.getMainHandStack();
        this.player = player;
    }


    public Elements getElement() {
        if (player.getMainHandStack().isEmpty()) {
            return Elements.PHYSICAL;
        }
        ItemStack stack = player.getMainHandStack();
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null || !nbtCompound.contains(ITEM_ELEMENT_KEY)) return Elements.PHYSICAL;
        Identifier elementId = new Identifier(nbtCompound.getString(ITEM_ELEMENT_KEY));
        Elements e = Elements.getElementWithId(elementId);

        return e;
    }

    public boolean setElement(Elements element) {
        if (ITEM_STACK.isEmpty()) {
            return false;
        }
        NbtCompound nbtCompound = ITEM_STACK.getOrCreateNbt();
        nbtCompound.putString(ITEM_ELEMENT_KEY, element.getId().toString());

        return true;
    }
}
