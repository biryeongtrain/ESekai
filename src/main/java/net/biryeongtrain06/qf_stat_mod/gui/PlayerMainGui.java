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
    private final Identifier SELECTION_MODIFIER_ID = TextHelper.getId("selection_modifier");
    private final int STRENGTH_SLOT_INDEX = 3;
    private final int CONSTITUTION_SLOT_INDEX = 4;
    private final int DEXTERITY_SLOT_INDEX = 5;
    private final int INTELLIGENCE_SLOT_INDEX = 6;
    private final int WISDOM_SLOT_INDEX = 7;
    private final int CHARISMA_SLOT_INDEX = 8;
    private PlayerStat playerStat;
    public PlayerMainGui(ServerPlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X1, player, false);
        this.playerStat = DataStorage.loadPlayerStat(player);
        this.updateSlot();
    }

    private void updateSlot() {
        PlayerHeadInfo headInfo = new PlayerHeadInfo(player);
        this.playerStat = DataStorage.loadPlayerStat(player);
        this.setSlot(0, headInfo.getHead());
        this.setSlot(STRENGTH_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.ORANGE_STAINED_GLASS_PANE)
                .setName(Text.empty().append(STRENGTH.getTranslatableName()).formatted(STRENGTH.getFormat()))
                .addLoreLine(Text.literal("Strength : " + playerStat.getTotalStatValue(STRENGTH)))
                .addLoreLine(Text.literal("Click To Increase Strength. ").formatted(Formatting.GREEN)));
        this.setSlot(CONSTITUTION_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.RED_STAINED_GLASS_PANE)
                .setName(Text.empty().append(CONSTITUTION.getTranslatableName()).formatted(CONSTITUTION.getFormat()))
                .addLoreLine(Text.literal("Constitution : " + playerStat.getTotalStatValue(CONSTITUTION)))
                .addLoreLine(Text.literal("Click To Increase Constitution. ").formatted(Formatting.GREEN)));
        this.setSlot(DEXTERITY_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.GREEN_STAINED_GLASS_PANE)
                .setName(Text.empty().append(StatTypes.DEXTERITY.getTranslatableName()).formatted(StatTypes.DODGE.getFormat()))
                .addLoreLine(Text.literal("Dexterity : " + playerStat.getTotalStatValue(DEXTERITY)))
                .addLoreLine(Text.literal("Click To Increase Dexterity. ").formatted(Formatting.GREEN)));
        this.setSlot(INTELLIGENCE_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.MAGENTA_STAINED_GLASS_PANE)
                .setName(Text.empty().append(INTELLIGENCE.getTranslatableName()).formatted(INTELLIGENCE.getFormat()))
                .addLoreLine(Text.literal("Intelligence : " + playerStat.getTotalStatValue(INTELLIGENCE)))
                .addLoreLine(Text.literal("Click To Increase Intelligence. ").formatted(Formatting.GREEN)));
        this.setSlot(WISDOM_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.PURPLE_STAINED_GLASS_PANE)
                .setName(Text.empty().append(StatTypes.WISDOM.getTranslatableName()).formatted(StatTypes.WISDOM.getFormat()))
                .addLoreLine(Text.literal("Wisdom : " + playerStat.getTotalStatValue(WISDOM)))
                .addLoreLine(Text.literal("Click To Increase Wisdom. ").formatted(Formatting.GREEN)));
        this.setSlot(CHARISMA_SLOT_INDEX, new GuiElementBuilder()
                .setItem(Items.YELLOW_STAINED_GLASS_PANE)
                .setName(Text.empty().append(CHARISMA.getTranslatableName()).formatted(CHARISMA.getFormat()))
                .addLoreLine(Text.literal("Charisma : " + playerStat.getTotalStatValue(CHARISMA)))
                .addLoreLine(Text.literal("Click To Increase Charisma. ").formatted(Formatting.GREEN)));
    }
    @Override
    public boolean onClick(int index, ClickType clickType, SlotActionType action, GuiElementInterface element) {
        if (index == 0) {
            return false;
        }
        StatTypes type = switch(index) {
            case (STRENGTH_SLOT_INDEX) -> STRENGTH;
            case (DEXTERITY_SLOT_INDEX) -> DEXTERITY;
            case (CONSTITUTION_SLOT_INDEX) -> CONSTITUTION;
            case (WISDOM_SLOT_INDEX) -> WISDOM;
            case (INTELLIGENCE_SLOT_INDEX) -> INTELLIGENCE;
            case (CHARISMA_SLOT_INDEX) -> CHARISMA;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
        playerStat.tryAddPerkInstance(player, type, SELECTION_MODIFIER_ID, 1, FLAT);
        DataStorage.savePlayerStat(player, playerStat);
        updateSlot();

            return super.onClick(index, clickType, action, element);
    }


}
