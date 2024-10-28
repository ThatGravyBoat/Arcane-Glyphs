package tech.thatgravyboat.glyphs.api;

import net.minecraft.ChatFormatting;

public enum GlyphElement {
    FIRE,
    WATER,
    EARTH,
    AIR,
    ;

    public final ChatFormatting color() {
        return switch (this) {
            case FIRE -> ChatFormatting.RED;
            case WATER -> ChatFormatting.BLUE;
            case EARTH -> ChatFormatting.DARK_GREEN;
            case AIR -> ChatFormatting.WHITE;
        };
    }
}
