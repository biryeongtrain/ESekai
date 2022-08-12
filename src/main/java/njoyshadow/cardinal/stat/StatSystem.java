package njoyshadow.cardinal.stat;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import njoyshadow.cardinal.component.StatComponent;


public class StatSystem extends EntityStat implements StatComponent, AutoSyncedComponent {

        private final PlayerEntity player;

    public StatSystem(int health, int defense, int dodge, int mana, int magic_damage, int attack_damage, ServerPlayerEntity player) {
            super(health, defense, dodge, mana, magic_damage, attack_damage);
            this.player = player;
        }

    public StatSystem(PlayerEntity player) {
            super(0, 0, 0, 0, 0, 0);
            this.player = player;
        }

        @Override
        public void setHealth(int health) {
            this.health = health;
        }

        @Override
        public void setDefense(int defense) {
            this.defense = defense;
        }

        @Override
        public void setDodge(int dodge) {
            this.dodge = dodge;
        }

        @Override
        public void setMana(int mana) {
            this.mana = mana;
        }

        @Override
        public void setMagic_damage(int magic_damage) {
            this.magic_damage = magic_damage;
        }

        @Override
        public void setAttack_damage() {
            this.attack_damage = attack_damage;
        }

        @Override
        public void readFromNbt(NbtCompound tag) {
            this.health = tag.getInt(HEALTH_KEY);
            this.defense = tag.getInt(DEFENSE_KEY);
            this.dodge = tag.getInt(DODGE_KEY);
            this.mana = tag.getInt(MANA_KEY);
            this.magic_damage = tag.getInt(MAGIC_DAMAGE_KEY);
            this.attack_damage = tag.getInt(ATTACK_DAMAGE_KEY);
        }

        @Override
        public void writeToNbt(NbtCompound tag) {
            tag.putInt(HEALTH_KEY, this.health);
            tag.putInt(DEFENSE_KEY, this.defense);
            tag.putInt(DODGE_KEY, this.dodge);
            tag.putInt(MANA_KEY, this.mana);
            tag.putInt(MAGIC_DAMAGE_KEY, this.magic_damage);
            tag.putInt(ATTACK_DAMAGE_KEY, this.attack_damage);
        }

        @Override
        public boolean shouldSyncWith(ServerPlayerEntity player) {
            return player.getUuid().equals(this.player.getUuid());
        }
}
