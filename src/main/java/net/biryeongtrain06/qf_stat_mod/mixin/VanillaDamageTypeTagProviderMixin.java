package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.register.QfTagBuilder;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.vanilla.VanillaDamageTypeTagProvider;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(VanillaDamageTypeTagProvider.class)
public class VanillaDamageTypeTagProviderMixin {

    private static QfTagBuilder tagBuilder;
    @Inject(method = "<init>", at = @At("HEAD"))
    private static void inject$customDamageTag(DataOutput output, CompletableFuture maxChainedNeighborUpdates, CallbackInfo ci) {
        tagBuilder = new QfTagBuilder(output, maxChainedNeighborUpdates);
    }
    @Inject(method = "configure", at = @At("HEAD"))
    public void inject$customDamageTag(RegistryWrapper.WrapperLookup lookup, CallbackInfo ci) {
        tagBuilder.configure(lookup);
        ci.cancel();
    }
}
