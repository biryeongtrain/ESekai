package net.biryeongtrain06.qf_stat_mod.utils.enums;

import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public enum EntityRank {
    UN_DECIDED("un_decided", 0, Formatting.WHITE, 1F, 0, 1F),
    COMMON("common", 3, Formatting.WHITE, 1, 82, 1F),
    RARE("rare", 5, Formatting.AQUA, 1.25F, 10, 1.5F),
    UNIQUE("unique", 7, Formatting.LIGHT_PURPLE, 2F, 5, 3F),
    LEGENDARY("legendary", 10, Formatting.GOLD, 5F, 2.5f, 5F),
    MYTHIC("mythic", 15, Formatting.RED, 7F, 0.5f, 10F);

    EntityRank(String name, int abilities, Formatting color, float expScaling, float spawnChance, float statMultiplier) {
        this.name = name;
        this.translationKey = "mob_rank_" + name;
        this.abilities = abilities;
        this.color = color;
        this.expScaling = expScaling;
        this.spawnChance = spawnChance;
        this.statMultiplier = statMultiplier;
    }

    private String name;

    public String getName() {
        return name;
    }

    private String translationKey;
    private int abilities;
    private Formatting color;
    private double expScaling;
    private float spawnChance;
    private float statMultiplier;
    public String getTranslationKey() {
        return translationKey;
    }

    public int getAbilities() {
        return abilities;
    }

    public Formatting getColor() {
        return color;
    }

    public float getSpawnChance() {
        return spawnChance;
    }

    public double getExpScaling() {
        return expScaling;
    }

    public void setExpScaling(double expScaling) {
        this.expScaling = expScaling;
    }

    public void setSpawnChance(float spawnChance) {
        this.spawnChance = spawnChance;
    }

    public float getStatMultiplier() {
        return statMultiplier;
    }

    public static List<EntityRank> getRanks() {
        List<EntityRank> list = Arrays.stream(values()).toList();
        list.remove(UN_DECIDED);
        return list;
    }

    public static EntityRank getRankById(String name) {
        for (EntityRank rank : values()) {
            if (rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }
}
