package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.biryeongtrain06.stat_system.MainStatSystem;
import net.biryeongtrain06.stat_system.stat.NonMagicWeaponItemStat;
import net.biryeongtrain06.stat_system.stat.NonMagicWeaponItemStatSystem;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;

public class ItemStatComponent implements ItemComponentInitializer {

    public static final ComponentKey<NonMagicWeaponComponentInterface> NON_MAGIC_WEAPON_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MainStatSystem.MOD_ID, "non_magic_weapon_component"), NonMagicWeaponComponentInterface.class);
    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {

    }
}
