package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.player.IServerPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements IServerPlayerEntity {
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
