package net.biryeongtrain06.qf_stat_mod.command;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain06.qf_stat_mod.gui.PlayerMainGui;
import net.biryeongtrain06.qf_stat_mod.interfaces.IDamageSource;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.EntityRank;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

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
            player.damage(iDamageSource.getQfDamageSourceWithPlayerAttack(damageSource, Elements.PHYSICAL, 20), 10);
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
            player.damage(iDamageSource.getQfDamageSourceWithPlayerAttack(damageSource, Elements.PHYSICAL, 40), 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
