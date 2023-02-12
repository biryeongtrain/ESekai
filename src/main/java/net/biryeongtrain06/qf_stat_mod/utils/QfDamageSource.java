package net.biryeongtrain06.qf_stat_mod.utils;

import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class QfDamageSource extends DamageSource {

    private final RegistryEntry<DamageType> type;
    @Nullable
    private final Entity attacker;
    @Nullable
    private final Entity source;
    @Nullable
    private final Vec3d position;
    private final Elements element;

    public QfDamageSource(RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Entity attacker, Elements element) {
        super(type, source, attacker);
        this.type = type;
        this.source = source;
        this.attacker = attacker;
        this.position = null;
        this.element = element;
    }


    @Override
    public Text getDeathMessage(LivingEntity killed) {
        String string = MOD_ID + "death." + this.getType().msgId() + ".player";
        Text text = this.attacker == null ? this.source.getDisplayName() : this.attacker.getDisplayName();
        Entity killer = this.attacker;
        ItemStack killerHeldItem;
        if (killer instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) killer;
            killerHeldItem = livingEntity.getMainHandStack();
        } else {
            killerHeldItem = ItemStack.EMPTY;
        }
        return !killerHeldItem.isEmpty() ? Text.translatable(string + ".item", new Object[]{killed.getDisplayName(), text, killerHeldItem.toHoverableText(), this.element.getTranslatableName()}) : Text.translatable(string , new Object[]{killed.getDisplayName(), text, this.element.getTranslatableName()});
    }
}
