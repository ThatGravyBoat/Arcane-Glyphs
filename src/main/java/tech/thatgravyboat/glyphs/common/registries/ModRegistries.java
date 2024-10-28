package tech.thatgravyboat.glyphs.common.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphType;
import tech.thatgravyboat.glyphs.common.glyphs.registry.GlyphRegistries;
import tech.thatgravyboat.glyphs.common.items.GlyphTome;

import java.util.function.Supplier;

public class ModRegistries {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Glyphs.MODID);
    private static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Glyphs.MODID);

    public static final DeferredItem<GlyphTome> TOME = ITEMS.register("tome", GlyphTome::new);
    public static final Supplier<DataComponentType<GlyphType>> GLYPH_DATA = COMPONENTS.registerComponentType(
            "glyph_data", builder -> builder.networkSynchronized(GlyphRegistries.PACKET_CODEC).persistent(GlyphRegistries.CODEC)
    );

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        COMPONENTS.register(bus);
    }
}
