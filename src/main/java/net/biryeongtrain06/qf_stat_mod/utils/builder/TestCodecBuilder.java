package net.biryeongtrain06.qf_stat_mod.utils.builder;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.stream.Stream;

public record TestCodecBuilder(String test) {
    public static final Codec<TestCodecBuilder> CODEC = new MapCodec.MapCodecCodec<>(new MapCodec<>() {
        @Override
        public <T> Stream<T> keys(DynamicOps<T> ops) {
            return Stream.concat(Stream.of(ops.createString("version")), CODEC_V1.keys(ops));
        }

        @Override
        public <T> DataResult<TestCodecBuilder> decode(DynamicOps<T> ops, MapLike<T> input) {
            var version = ops.getNumberValue(input.get("version"), 1).intValue();
            return switch (version) {
                case 1 -> CODEC_V1.decode(ops, input);
                default -> throw new IllegalStateException("Unexpected value: " + version);
            };

        }

        @Override
        public <T> RecordBuilder<T> encode(TestCodecBuilder input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
            return CODEC_V1.encode(input, ops, prefix.add("version", ops.createInt(1)));
        }
    });

    public static MapCodec<TestCodecBuilder> CODEC_V1 = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
                Codec.STRING.optionalFieldOf("test", "none").forGetter(TestCodecBuilder::test)
        ).apply(instance, TestCodecBuilder::new)
    );

}
