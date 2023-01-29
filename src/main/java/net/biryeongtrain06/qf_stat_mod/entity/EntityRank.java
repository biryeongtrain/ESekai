package net.biryeongtrain06.qf_stat_mod.entity;

import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public enum EntityRank {
    UN_DECIDED("un_decided", 0, Formatting.WHITE, 0F, 0),
    COMMON("common", 0, Formatting.WHITE, 1, 82),
    RARE("rare", 1, Formatting.AQUA, 1.25F, 10),
    UNIQUE("unique", 3, Formatting.LIGHT_PURPLE, 1.75F, 5),
    LEGENDARY("legendary", 5, Formatting.GOLD, 3F, 2.5f),
    MYTHIC("mythic", 7, Formatting.RED, 5F, 0.5f);

    EntityRank(String translationKey, int abilities, Formatting color, float expScaling, float spawn_chance) {
        this.translationKey = "mob_rank" + translationKey;
        this.abilities = abilities;
        this.color = color;
        this.expScaling = expScaling;
        this.spawn_chance = spawn_chance;
    }

    private String translationKey;
    private int abilities;
    private Formatting color;
    private double expScaling;
    private float spawn_chance;

    public String getTranslationKey() {
        return translationKey;
    }

    public int getAbilities() {
        return abilities;
    }

    public Formatting getColor() {
        return color;
    }

    public float getSpawn_chance() {
        return spawn_chance;
    }

    public double getExpScaling() {
        return expScaling;
    }

    public void setExpScaling(double expScaling) {
        this.expScaling = expScaling;
    }

    public void setSpawn_chance(float spawn_chance) {
        this.spawn_chance = spawn_chance;
    }

    public static List<EntityRank> getRanks() {
        List<EntityRank> list = Arrays.stream(values()).toList();
        list.remove(UN_DECIDED);
        return list;
    }

}
