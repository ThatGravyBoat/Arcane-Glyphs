package tech.thatgravyboat.glyphs.common.glyphs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphElement;
import tech.thatgravyboat.glyphs.api.GlyphType;
import tech.thatgravyboat.glyphs.common.registries.ModBlocks;

import java.util.List;

public class LightGlyph extends GlyphType {

    @Override
    public ResourceLocation id() {
        return Glyphs.id("light");
    }

    @Override
    public List<GlyphElement> elements() {
        return List.of(
                GlyphElement.FIRE,
                GlyphElement.FIRE,
                GlyphElement.AIR,
                GlyphElement.FIRE,
                GlyphElement.FIRE
        );
    }

    @Override
    public GlyphElement mainElement() {
        return GlyphElement.AIR;
    }

    @Override
    public int cost() {
        return 2;
    }

    @Override
    public boolean use(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos().relative(context.getClickedFace());
        var state = level.getBlockState(pos);
        if (state.isAir()) {
            level.setBlock(pos, ModBlocks.LIGHT.get().defaultBlockState(), Block.UPDATE_ALL);
            return true;
        }
        return false;
    }
}
