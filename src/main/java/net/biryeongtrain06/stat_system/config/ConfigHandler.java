package net.biryeongtrain06.stat_system.config;

import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import net.biryeongtrain06.stat_system.MainStatSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ConfigHandler {
    public static PropertyMirror<Integer> maxLevel = PropertyMirror.create(ConfigTypes.INTEGER);
    public static PropertyMirror<Integer> levelPerDistance = PropertyMirror.create(ConfigTypes.INTEGER);

    private static final ConfigTree CONFIG = ConfigTree.builder()
            .beginValue("maxLevel", ConfigTypes.INTEGER, 100)
            .withComment("This decides Player & Entity's Max level.")
            .finishValue(maxLevel::mirror)

            .beginValue("levelPerDistance", ConfigTypes.INTEGER, 250)
            .withComment("This decides Entity Level Scaling Distance When you turn off the nearByPlayer Setting.")
            .finishValue(levelPerDistance::mirror)

            .build();

    private static void writeDefaultConfig(Path path, JanksonValueSerializer serializer) {
        try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
            FiberSerialization.serialize(CONFIG, s, serializer);
        } catch (IOException ignored) {}
    }

    public static void setup() {
        JanksonValueSerializer serializer = new JanksonValueSerializer(false);
        Path p = Paths.get("config", MainStatSystem.MOD_ID + ".json");
        writeDefaultConfig(p, serializer);

        try (InputStream s = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
            FiberSerialization.deserialize(CONFIG, s, serializer);
        } catch (IOException | ValueDeserializationException e) {
            System.out.println("Error loading config");
        }
    }
}

