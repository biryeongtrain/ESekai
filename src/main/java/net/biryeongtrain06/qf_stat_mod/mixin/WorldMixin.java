package net.biryeongtrain06.qf_stat_mod.mixin;

import net.biryeongtrain06.qf_stat_mod.register.QfTestDamageSource;
import net.minecraft.class_8109;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(World.class)
public class WorldMixin {

    @Mutable
    @Shadow @Final private class_8109 field_42476;

    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;field_42476:Lnet/minecraft/class_8109;", opcode = Opcodes.PUTFIELD))
    private void changeDamageSource(MutableWorldProperties properties, RegistryKey registryRef, DynamicRegistryManager dynamicRegistryManager, RegistryEntry registryEntry, Supplier supplier, boolean bl, boolean bl2, long l, int i, CallbackInfo ci) {
        this.field_42476 = new QfTestDamageSource(dynamicRegistryManager);
    }
}
