package net.biryeongtrain06.stat_system.stat;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.biryeongtrain06.stat_system.component.StatComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class StatSystem extends EntityStat implements StatComponent, AutoSyncedComponent {

    private final PlayerEntity player;

    public StatSystem(int health, int defense, int dodge, int mana, int magic_damage, int attack_damage, int xp, int level, ServerPlayerEntity player) {
        super(health, defense, dodge, mana, magic_damage, attack_damage, xp, level);
        this.player = player;
    }

    public StatSystem(PlayerEntity player) {
        super(0, 0, 0, 0, 0, 0, 0, 1);
        this.player = player;
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
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public void addDefense(int defense) {
        this.defense += defense;
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
    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public void addMana(int mana) {
        this.mana += mana;
    }

    @Override
    public void setMagic_damage(int magic_damage) {
        this.magic_damage = magic_damage;
    }

    @Override
    public void addMagic_damage(int magic_damage) {
        this.magic_damage += magic_damage;
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
    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void addLevel(int level) {
        this.level += level;
        this.player.sendMessage(Text.literal("Level Up! Now your Level is " + this.level));
    }

    @Override
    public void addXp(int xp) {
        this.xp += xp;
        if (this.xp > 1) {
            return;
        }
        while (this.xp >= 1) {
            this.addLevel(1);
            this.xp -= 1;
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.health = tag.getInt(HEALTH_KEY);
        this.defense = tag.getInt(DEFENSE_KEY);
        this.dodge = tag.getInt(DODGE_KEY);
        this.mana = tag.getInt(MANA_KEY);
        this.magic_damage = tag.getInt(MAGIC_DAMAGE_KEY);
        this.attack_damage = tag.getInt(ATTACK_DAMAGE_KEY);
        this.xp = tag.getInt(XP_KEY);
        this.level = tag.getInt(LEVEL_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(HEALTH_KEY, this.health);
        tag.putInt(DEFENSE_KEY, this.defense);
        tag.putInt(DODGE_KEY, this.dodge);
        tag.putInt(MANA_KEY, this.mana);
        tag.putInt(MAGIC_DAMAGE_KEY, this.magic_damage);
        tag.putInt(ATTACK_DAMAGE_KEY, this.attack_damage);
        tag.putInt(XP_KEY, this.xp);
        tag.putInt(LEVEL_KEY, this.level);
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player.getUuid().equals(this.player.getUuid());
    }
}
