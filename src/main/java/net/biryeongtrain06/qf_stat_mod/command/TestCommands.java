package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.api.ItemStats;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerMainGui;
import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.EntityRank;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.FLAT;
import static net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag.PERCENT;

public class TestCommands {
    public static int actionbar(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            ServerPlayNetworkHandler playNetworkHandler = player.networkHandler;
            player.sendMessage(Text.literal("test"));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getRarity(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            player.sendMessage(Text.literal("Common : " + EntityRank.COMMON.getSpawnChance()));
            player.sendMessage(Text.literal("Rare : " + EntityRank.RARE.getSpawnChance()));
            player.sendMessage(Text.literal("Unique : " + EntityRank.UNIQUE.getSpawnChance()));
            player.sendMessage(Text.literal("Legendary : " + EntityRank.LEGENDARY.getSpawnChance()));
            player.sendMessage(Text.literal("Mythic : " + EntityRank.MYTHIC.getSpawnChance()));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    public static int openGUI(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            PlayerMainGui playerMainGui = new PlayerMainGui(player);
            playerMainGui.open();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    public static int damageTest(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            DamageSource damageSource = player.getDamageSources().playerAttack(player);
            IDamageSource iDamageSource = (IDamageSource) player.getDamageSources();
            player.damage(iDamageSource.getQfDamageSourceWithMeleeAttack(damageSource, Elements.PHYSICAL, 20), 10);
        } catch(Exception e){
            e.printStackTrace();
        }
        return 1;
    }
    public static int selfDamage(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            DamageSource damageSource = player.getDamageSources().playerAttack(player);
            IDamageSource iDamageSource = (IDamageSource) player.getDamageSources();
            player.damage(iDamageSource.getQfDamageSourceWithMeleeAttack(damageSource, Elements.PHYSICAL, 40), 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getElement(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            Elements e = ItemStats.getPlayerItemElement(player);
            player.sendMessage(Text.literal(e.getIcon()).append(e.getTranslatableName()).formatted(e.getFormat()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int setElement(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            Elements e = Elements.FIRE;
            boolean b = ItemStats.setPlayerItemElement(e, player);
            if (!b) {
                return 0;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int setItemDamage(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            ItemStack stack = player.getMainHandStack();
            if (!ItemStats.setItemDamage(stack, 100f)) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getItemDamage(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            ItemStack stack = player.getMainHandStack();
            float damage = (float) ItemStats.getItemDamage(stack);
            player.sendMessage(Text.literal(String.valueOf(damage)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getItemStat(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            ItemStack stack = player.getMainHandStack();
            float value = ItemStats.getItemStat(stack, StatTypes.HEALTH, FLAT);
            player.sendMessage(Text.literal(String.valueOf(value)));
            value = ItemStats.getItemStat(stack, StatTypes.HEALTH, PERCENT);
            player.sendMessage(Text.literal(String.valueOf(value)));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int setItemStat(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            ItemStack stack = player.getMainHandStack();
            if(!ItemStats.setItemStat(stack, StatTypes.HEALTH, FLAT,  10)) return 0;
            if(!ItemStats.setItemStat(stack, StatTypes.HEALTH, PERCENT, 0.5F)) return 0;
            player.sendMessage(Text.literal("Health is changed"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
