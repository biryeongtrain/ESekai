package net.biryeongtrain06.stat_system.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import it.unimi.dsi.fastutil.Hash;
import net.biryeongtrain06.stat_system.MainStatSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class EntityStatSystem implements StatSystem, AutoSyncedComponent {

    private final ServerPlayerEntity player;
    public int level = 0;
    public HashMap<Identifier, StatEntry> entries = new HashMap<>();

    public EntityStatSystem(ServerPlayerEntity player) {
        this.player = player;
    }
    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void addLevel() { this.level += 1; }

    @Override
    public void setLevel() {

    }

    @Override
    public void levelUp() {

    }

    @Override
    public void readFromNbt(NbtCompound tag) {

    }

    @Override
    public void writeToNbt(NbtCompound tag) {

    }

}

