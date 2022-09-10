package net.biryeongtrain06.stat_system.util.setItemStat;

import net.biryeongtrain06.stat_system.util.setItemStat.statRegistry.*;

import java.util.ArrayList;
import java.util.List;

public class ItemStatKeys {
    public static final String ELEMENT_KEY = "element";
    public static final String EQUIPMENT_LEVEL_KEY = "item_level";
    public static final String ITEM_HEALTH_KEY = "item_health";
    public static final String ITEM_MANA_KEY = "item_mana";
    public static final String ITEM_STRENGTH_KEY = "item_strength";
    public static final String ITEM_DEXTERITY_KEY = "item_dexterity";
    public static final String ITEM_INTELLIGENCE_KEY = "item_intelligence";
    public static final String ITEM_LUCK_KEY = "item_luck";
    public static String ITEM_ATTACK_DAMAGE_KEY = "item_attack_damage";
    public static String ITEM_MAGIC_DAMAGE_KEY = "item_magic_damage";
    public static String ITEM_DEFENSE_KEY = "item_defense_damage";
    public static String ITEM_DODGE_KEY = "item_dodge_key";



    public static ArrayList getSTAT_LIST() {
        ArrayList<String> STAT_LIST = new ArrayList<>();
        STAT_LIST.add(ITEM_HEALTH_KEY);
        STAT_LIST.add(ITEM_MANA_KEY);
        STAT_LIST.add(ITEM_STRENGTH_KEY);
        STAT_LIST.add(ITEM_DEXTERITY_KEY);
        STAT_LIST.add(ITEM_DEXTERITY_KEY);
        STAT_LIST.add(ITEM_INTELLIGENCE_KEY);
        STAT_LIST.add(ITEM_LUCK_KEY);

        return STAT_LIST;
    }

    public static StatRegistry getKeyRegistry(String s) {
        if (s.equals(ITEM_HEALTH_KEY)) {
            return new HealthRegistry();
        }
        if (s.equals(ITEM_DEFENSE_KEY)) {
            return new DefenseRegistry();
        }
        if (s.equals(ITEM_DEXTERITY_KEY)) {
            return new DexterityRegistry();
        }
        if (s.equals(ITEM_DODGE_KEY)) {
            return new DodgeRegistry();
        }
        if (s.equals(ITEM_MANA_KEY)) {
            return new ManaRegistry();
        }
        if (s.equals(ITEM_STRENGTH_KEY)) {
            return new StrengthRegistry();
        }
        if (s.equals(ITEM_INTELLIGENCE_KEY)) {
            return new IntelligenceRegistry();
        }
        if (s.equals(ITEM_LUCK_KEY)) {
            return new LuckRegistry();
        }
        if (s.equals(ITEM_ATTACK_DAMAGE_KEY)) {
            return new AttackDamageRegistry();
        }
        if (s.equals(ITEM_MAGIC_DAMAGE_KEY)) {
            return new MagicDamageRegistry();
        }
        return null;
    }
}