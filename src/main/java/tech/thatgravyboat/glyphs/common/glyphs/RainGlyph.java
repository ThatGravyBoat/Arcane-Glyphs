package tech.thatgravyboat.glyphs.common.glyphs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphElement;
import tech.thatgravyboat.glyphs.api.GlyphType;

import java.util.List;

public class RainGlyph extends GlyphType {

    @Override
    public ResourceLocation id() {
        return Glyphs.id("rain");
    }

    @Override
    public List<GlyphElement> elements() {
        return List.of(
                GlyphElement.WATER,
                GlyphElement.WATER,
                GlyphElement.WATER,
                GlyphElement.WATER
        );
    }

    @Override
    public GlyphElement mainElement() {
        return GlyphElement.WATER;
    }

    @Override
    public int cost() {
        return 15;
    }

    @Override
    public boolean use(Player player, InteractionHand hand) {
        var level = player.level();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(
                    0,
                    ServerLevel.RAIN_DURATION.sample(serverLevel.random),
                    true, false
            );
        }
        return true;
    }
}
