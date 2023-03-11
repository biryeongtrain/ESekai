package net.biryeongtrain06.qf_stat_mod.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.biryeongtrain06.qf_stat_mod.stats.interfaces.IStats;
import net.biryeongtrain06.qf_stat_mod.utils.enums.Elements;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatSubTag;
import net.biryeongtrain06.qf_stat_mod.utils.enums.StatTypes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;

import java.util.HashMap;

@SuppressWarnings("unused")
public interface INewCommonEntityComponents extends Component, ServerTickingComponent {

    void addTemporaryHealthInstance(EntityAttributeModifier modifier);
    void addPersistentHealthInstance(EntityAttributeModifier modifier);
    void setBaseHealth(int value);
    void addTemporaryDamageInstance(EntityAttributeModifier modifier);
    void addPersistentDamageInstance(EntityAttributeModifier modifier);
    void setBaseDamage(int value);
    void setLevel(int value);
    void setElement(Elements element);
    void addStatInstance(StatTypes type, Identifier id, float value, StatSubTag tag);
    boolean replaceStatInstance(StatTypes type, Identifier id, float value, StatSubTag tag);
    boolean hasStatInstance(StatTypes type, Identifier id, StatSubTag tag);
    float getTotalStatValue(StatTypes type);
    IStats getStatClass(StatTypes stat);
    Elements getElement();
}
