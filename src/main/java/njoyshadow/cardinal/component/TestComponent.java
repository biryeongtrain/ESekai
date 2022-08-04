package njoyshadow.cardinal.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import static njoyshadow.cardinal.Cardinal.MOD_ID;

public class TestComponent implements ItemComponentInitializer {

    public static final ComponentKey<IntComponent> MAGIK =
            ComponentRegistry.getOrCreate(new Identifier(MOD_ID, "magik"), IntComponent.class);

    public static void useMagik(Entity player, Entity result){
        int magik = MAGIK.get(player).getValue();
        if(result instanceof LivingEntity){
            MAGIK.get(player).increment();

            if(magik > 100){
                result.damage(DamageSource.ANVIL,9999);
                MAGIK.get(player).setValue(magik - 100);
                System.out.println("뒤졌노");
            }
            System.out.println(String.format("magik : %s",magik));
        }
    }


    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(item -> item instanceof SwordItem, MAGIK, ItemIntComponent::new);
        System.out.println("Cardinal Registry");
    }
}
