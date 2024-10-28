package tech.thatgravyboat.glyphs.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.common.registries.ModRegistries;

@Mod(value = Glyphs.MODID, dist = Dist.CLIENT)
public class GlyphsClient {

    public GlyphsClient(IEventBus bus) {
        bus.addListener(GlyphsClient::onItemProperties);
    }

    public static void onItemProperties(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
                ItemProperties.register(
                        ModRegistries.TOME.get(),
                        Glyphs.id("type"),
                        (stack, level, entity, seed) -> {
                            var glyph = stack.get(ModRegistries.GLYPH_DATA);
                            if (glyph == null) return 0f;
                            return glyph.mainElement().ordinal() + 1;
                        }
                )
        );
    }
}
