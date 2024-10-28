package tech.thatgravyboat.glyphs;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import tech.thatgravyboat.glyphs.common.glyphs.registry.BuiltInGlyphs;
import tech.thatgravyboat.glyphs.common.network.NetworkHandler;
import tech.thatgravyboat.glyphs.common.registries.ModBlocks;
import tech.thatgravyboat.glyphs.common.registries.ModRegistries;

@Mod(Glyphs.MODID)
public class Glyphs {

    public static final String MODID = "glyphs";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Glyphs(IEventBus bus) {
        ModBlocks.BLOCKS.register(bus);
        ModRegistries.init(bus);
        BuiltInGlyphs.init();

        NetworkHandler.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
