package net.biryeongtrain06.qf_stat_mod.gui;

import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementInterface;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.player.PlayerHeadInfo;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerMainGui extends SimpleGui {

    final int STRENGTH_SLOT_INDEX = 3;
    final int DEXTERITY_SLOT_INDEX = 5;
    final int WISDOM_SLOT_INDEX = 7;
    private PlayerStat playerStat;
    public PlayerMainGui(ServerPlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X1, player, false);
        this.updateSlot();
    }

    private void updateSlot() {
        PlayerHeadInfo headInfo = new PlayerHeadInfo(player);
        this.playerStat = DataStorage.loadPlayerStat(player);
        this.setSlot(0, headInfo.getHead());
        this.setSlot(STRENGTH_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.RED_STAINED_GLASS_PANE)
                .setName(Text.empty().append(StatTypes.STRENGTH.translatableName).formatted(StatTypes.STRENGTH.getFormat()))
                .addLoreLine(Text.literal("Strength : " + playerStat.getStrength()))
                .addLoreLine(Text.literal("Click To Increase Strength. ").formatted(Formatting.GREEN)));
        this.setSlot(DEXTERITY_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.GREEN_STAINED_GLASS_PANE)
                .setName(Text.empty().append(StatTypes.DEXTERITY.translatableName).formatted(StatTypes.DODGE.getFormat()))
                .addLoreLine(Text.literal("Dexterity : " + playerStat.getDexterity()))
                .addLoreLine(Text.literal("Click To Increase Dexterity. ").formatted(Formatting.GREEN)));
        this.setSlot(WISDOM_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.PURPLE_STAINED_GLASS_PANE)
                .setName(Text.empty().append(StatTypes.WISDOM.translatableName).formatted(StatTypes.WISDOM.getFormat()))
                .addLoreLine(Text.literal("Wisdom : " + playerStat.getWisdom()))
                .addLoreLine(Text.literal("Click To Increase Wisdom. ").formatted(Formatting.GREEN)));
    }
    @Override
    public boolean onClick(int index, ClickType clickType, SlotActionType action, GuiElementInterface element) {
        if (playerStat.tryRemoveSelectPoint()) {
            if (index == STRENGTH_SLOT_INDEX) {
                playerStat.addStrength(player,1);
            } else if (index == DEXTERITY_SLOT_INDEX) {
                playerStat.addDexterity(1);
            } else if (index == WISDOM_SLOT_INDEX) {
                playerStat.addWisdom(1);
            }
            DataStorage.savePlayerStat(player, playerStat);
            updateSlot();
        }
            return super.onClick(index, clickType, action, element);
    }


}
