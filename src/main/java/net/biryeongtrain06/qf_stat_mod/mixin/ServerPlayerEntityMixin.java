package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.api.DataStorage;
import net.biryeongtrain06.qf_stat_mod.api.PlayerStat;
import net.biryeongtrain06.qf_stat_mod.callback.PlayerHitByEntityCallback;
import net.biryeongtrain06.qf_stat_mod.damage.QfDamageSource;
import net.biryeongtrain06.qf_stat_mod.interfaces.IServerPlayerEntityDuck;
import net.biryeongtrain06.qf_stat_mod.utils.PlayerHelper;
import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class   ServerPlayerEntityMixin implements IServerPlayerEntityDuck {
    private boolean isPlayedBefore = false;
    private boolean isDisplaySystemMessage = true;

    @Inject(at = @At("RETURN"), method = ("writeCustomDataToNbt"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("isPlayedBefore", this.isPlayedBefore);
        nbt.putBoolean("isDisplaySystemMessage", this.isDisplaySystemMessage);
    }

    @Inject(at = @At("RETURN"), method = ("readCustomDataFromNbt"))
    public void readCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        this.isPlayedBefore = nbt.getBoolean("isPlayedBefore");
        this.isDisplaySystemMessage = nbt.getBoolean("isDisplaySystemMessage");
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void applyDamageHook(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!(player instanceof ServerPlayerEntity)) return;
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
        if ((source instanceof QfDamageSource)) return;
        if (source.getSource() instanceof ProjectileEntity) {

        }
        if(source.getAttacker() instanceof LivingEntity || source.getAttacker() == null) {
            PlayerHitByEntityCallback.EVENT.invoker().onHit((ServerPlayerEntity) player, source.getSource(), source, amount);
        }
        cir.cancel();
    }

    @Inject(at = @At("HEAD"), method = ("tick"))
    public void displayHealthBar(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        player.sendMessage(
                Text.empty()
                        .append(Text.translatable(TextHelper.createTranslation("health")).formatted(Formatting.RED))
                            .append(Text.literal(" : " + (int) playerStat.getCurrentHealth()))
                        .formatted(PlayerHelper.getPlayerHealthFormat(player))
                        .append(Text.literal(" / " + playerStat.getMaxHealth()))
                        .append(Text.literal("         "))
                        .append(Text.translatable(TextHelper.createTranslation("mana")).formatted(Formatting.BLUE))
                        .append(Text.literal(" : " + (int) playerStat.getCurrentMana()))
                        .append(Text.literal(" / " + playerStat.getMaxMana()))
                ,true
        );
    }

    @Inject(at = @At("RETURN"), method = ("tick"))
    public void qfCustomHeal(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        PlayerStat playerStat = DataStorage.loadPlayerStat(player);
        playerStat.tick(player);
        DataStorage.savePlayerStat(player, playerStat);
    }
    @Override
    public boolean isPlayedBefore() {
        return this.isPlayedBefore;
    }

    @Override
    public void setPlayedBefore(boolean val) {
        this.isPlayedBefore = val;
    }

    @Override
    public boolean isDisplaySystemMessage() {
        return this.isDisplaySystemMessage;
    }

    @Override
    public void setDisplaySystemMessage(boolean val) {
        this.isDisplaySystemMessage = val;
    }
}
