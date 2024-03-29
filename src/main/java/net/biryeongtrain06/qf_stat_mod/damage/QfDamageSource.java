package net.biryeongtrain06.qf_stat_mod.damage;

import net.biryeongtrain06.qf_stat_mod.utils.TextHelper;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static net.biryeongtrain06.qf_stat_mod.MainStatSystem.MOD_ID;

public class QfDamageSource extends DamageSource {

    @Nullable
    private final Elements element;
    private final DamageSource originalDamageSource;
    private final float originalDamageAmount;

    public QfDamageSource(RegistryEntry<DamageType> type, DamageSource originalDamageSource, Elements element, float originalDamageAmount) {
        super(type, originalDamageSource.getSource(), originalDamageSource.getAttacker());
        this.originalDamageSource = originalDamageSource;
        this.element = element;
        this.originalDamageAmount = originalDamageAmount;
    }

    public DamageSource getOriginalDamageSource() {
        return originalDamageSource;
    }

    public float getOriginalDamageAmount() {
        return originalDamageAmount;
    }

    public Elements getElement() {
        return element;
    }

    @Override
    public Text getDeathMessage(LivingEntity killed) {
        if (this.getSource() == null && this.getAttacker() == null) return originalDamageSource.getDeathMessage(killed);
        String string = MOD_ID + ".death." + this.getType().msgId() + ".player";
        Text text = this.getAttacker() == null ? this.getSource().getDisplayName() : this.getAttacker().getDisplayName();
        if (this.getAttacker() == null && this.getSource() == null) {
            LivingEntity livingEntity2 = killed.getPrimeAdversary();
            return livingEntity2 != null ? Text.translatable(string + ".environment", new Object[]{killed.getDisplayName(), livingEntity2.getDisplayName()}) : Text.translatable(string, new Object[]{killed.getDisplayName()});
        }
        Entity killer = this.getAttacker();
        ItemStack killerHeldItem;
        if (killer instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) killer;
            killerHeldItem = livingEntity.getMainHandStack();
        } else {
            killerHeldItem = ItemStack.EMPTY;
        }
        return !killerHeldItem.isEmpty() ?
                Text.translatable(string + ".item", new Object[]{killed.getDisplayName(), text, TextHelper.getDeathMessageItemHoverableText(killerHeldItem), this.element.toHoverTextWithIcon()}) :
                Text.translatable(string , new Object[]{killed.getDisplayName(), text, this.element.toHoverTextWithIcon()});

    }
}
