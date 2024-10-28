package tech.thatgravyboat.glyphs.common.glyphs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.BonemealableBlock;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphElement;
import tech.thatgravyboat.glyphs.api.GlyphType;

import java.util.List;

public class GrowthGlyph extends GlyphType {

    @Override
    public ResourceLocation id() {
        return Glyphs.id("growth");
    }

    @Override
    public List<GlyphElement> elements() {
        return List.of(
                GlyphElement.EARTH,
                GlyphElement.EARTH,
                GlyphElement.EARTH,
                GlyphElement.EARTH
        );
    }

    @Override
    public GlyphElement mainElement() {
        return GlyphElement.EARTH;
    }

    @Override
    public boolean use(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);
        var block = state.getBlock();
        if (block instanceof BonemealableBlock bonemealable) {
            if (bonemealable.isValidBonemealTarget(level, pos, state)) {
                if (level instanceof ServerLevel serverLevel && bonemealable.isBonemealSuccess(level, level.random, pos, state)) {
                    bonemealable.performBonemeal(serverLevel, level.random, pos, state);
                }
                return true;
            }
        }
        return false;
    }
}
