package tech.thatgravyboat.glyphs.common.glyphs.registry;

import tech.thatgravyboat.glyphs.common.glyphs.*;

import static tech.thatgravyboat.glyphs.common.glyphs.registry.GlyphRegistries.register;

public class BuiltInGlyphs {

    public static void init() {
        register(new FireGlyph());
        register(new GrowthGlyph());
        register(new LightGlyph());
        register(new RainGlyph());
        register(new ThunderGlyph());
        register(new FireBallGlyph());
    }
}
