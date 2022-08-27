package net.biryeongtrain06.stat_system.stat;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.biryeongtrain06.stat_system.component.EntityStatComponentInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class EntityStatSystem extends EntityStat implements EntityStatComponentInterface, AutoSyncedComponent {

    private final Entity entity;

    public EntityStatSystem(int health, int defense, int dodge, int attack_damage, int level, int difficulty, HostileEntity entity) {
        super(health, defense, dodge, attack_damage, level, difficulty);
        this.entity = entity;
    }

    public EntityStatSystem(Entity entity) {
        super(20, 0, 0, 1, 1, 0);
        this.entity = entity;
    }


    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void addHealth(int health) {
        this.health += health;
    }

    @Override
    public int getDefense() {
        return this.defense;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public void addDefense(int defense) {
        this.defense += defense;
    }

    @Override
    public int getDodge() {
        return this.dodge;
    }

    @Override
    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    @Override
    public void addDodge(int dodge) {
        this.dodge += dodge;
    }

    @Override
    public int getAttack_damage() {
        return this.attack_damage;
    }

    @Override
    public void setAttack_damage(int attack_damage) {
        this.attack_damage = attack_damage;
    }

    @Override
    public void addAttack_damage(int attack_damage) {
        this.attack_damage += attack_damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }


    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.health = tag.getInt("health");
        this.defense = tag.getInt("defense");
        this.dodge = tag.getInt("dodge");
        this.attack_damage = tag.getInt("attack_damage");
        this.level = tag.getInt("level");
        this.difficulty = tag.getInt("difficulty");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("health", this.health);
        tag.putInt("defense", this.defense);
        tag.putInt("dodge", this.dodge);
        tag.putInt("attack_damage", this.attack_damage);
        tag.putInt("level", this.level);
        tag.putInt("difficulty", this.difficulty);
    }
}
