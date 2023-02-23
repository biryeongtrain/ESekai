package net.biryeongtrain06.qf_stat_mod.gui;

import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementInterface;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.player.PlayerHeadInfo;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.FLAT;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes.*;

public class PlayerMainGui extends SimpleGui {
    final Identifier SELECTION_MODIFIER = TextHelper.getId("selection_modifier");
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
                .setName(Text.empty().append(STRENGTH.translatableName).formatted(STRENGTH.getFormat()))
                .addLoreLine(Text.literal("Strength : " + playerStat.getTotalStatValue(STRENGTH)))
                .addLoreLine(Text.literal("Click To Increase Strength. ").formatted(Formatting.GREEN)));
        this.setSlot(DEXTERITY_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.GREEN_STAINED_GLASS_PANE)
                .setName(Text.empty().append(StatTypes.DEXTERITY.translatableName).formatted(StatTypes.DODGE.getFormat()))
                .addLoreLine(Text.literal("Dexterity : " + playerStat.getTotalStatValue(DEXTERITY)))
                .addLoreLine(Text.literal("Click To Increase Dexterity. ").formatted(Formatting.GREEN)));
        this.setSlot(WISDOM_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.PURPLE_STAINED_GLASS_PANE)
                .setName(Text.empty().append(StatTypes.WISDOM.translatableName).formatted(StatTypes.WISDOM.getFormat()))
                .addLoreLine(Text.literal("Wisdom : " + playerStat.getTotalStatValue(WISDOM)))
                .addLoreLine(Text.literal("Click To Increase Wisdom. ").formatted(Formatting.GREEN)));
    }
    @Override
    public boolean onClick(int index, ClickType clickType, SlotActionType action, GuiElementInterface element) {

        if (index == STRENGTH_SLOT_INDEX) {
            playerStat.tryAddPerkInstance(player, STRENGTH, SELECTION_MODIFIER, 1, FLAT);
        } else if (index == DEXTERITY_SLOT_INDEX) {
            playerStat.tryAddPerkInstance(player, DEXTERITY, SELECTION_MODIFIER, 1, FLAT);
        } else if (index == WISDOM_SLOT_INDEX) {
            playerStat.tryAddPerkInstance(player, WISDOM, SELECTION_MODIFIER, 1, FLAT);
        }
        DataStorage.savePlayerStat(player, playerStat);
        updateSlot();

            return super.onClick(index, clickType, action, element);
    }


}
