package tech.thatgravyboat.glyphs.common.glyphs.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.Optionull;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import tech.thatgravyboat.glyphs.api.GlyphElement;
import tech.thatgravyboat.glyphs.api.GlyphType;

import java.util.*;

public class GlyphRegistries {

    private static final Map<ResourceLocation, GlyphType> TYPES = new HashMap<>();
    public static final Codec<GlyphType> CODEC = ResourceLocation.CODEC.flatXmap(
            id -> Optionull.mapOrDefault(
                    TYPES.get(id),
                    DataResult::success,
                    DataResult.error(() -> "Unknown type: " + id)
            ),
            type -> DataResult.success(type.id())
    );
    public static final StreamCodec<? super ByteBuf, GlyphType> PACKET_CODEC = ResourceLocation.STREAM_CODEC.map(
            TYPES::get,
            GlyphType::id
    );

    public static void register(GlyphType type) {
        TYPES.put(type.id(), type);
    }

    public static GlyphType get(ResourceLocation id) {
        return TYPES.get(id);
    }

    public static GlyphType get(GlyphElement[] elements) {
        List<GlyphElement> list = Arrays.stream(elements).filter(Objects::nonNull).toList();
        return TYPES.values().stream()
            .filter(type -> type.elements().equals(list))
            .findFirst()
            .orElse(null);
    }
}
