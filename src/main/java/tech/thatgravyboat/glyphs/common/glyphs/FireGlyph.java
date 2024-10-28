package tech.thatgravyboat.glyphs.common.glyphs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphElement;
import tech.thatgravyboat.glyphs.api.GlyphType;

import java.util.List;

public class FireGlyph extends GlyphType {

    @Override
    public ResourceLocation id() {
        return Glyphs.id("fire");
    }

    @Override
    public List<GlyphElement> elements() {
        return List.of(
                GlyphElement.FIRE,
                GlyphElement.FIRE,
                GlyphElement.FIRE,
                GlyphElement.FIRE
        );
    }

    @Override
    public GlyphElement mainElement() {
        return GlyphElement.FIRE;
    }

    @Override
    public int cost() {
        return 0;
    }

    @Override
    public boolean canPerformAbility(ItemAbility ability) {
        return ability == ItemAbilities.FIRESTARTER_LIGHT;
    }

    @Override
    public boolean use(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);
        var newState = state.getToolModifiedState(context, ItemAbilities.FIRESTARTER_LIGHT, false);
        if (newState != null) {
            level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
            return true;
        } else {
            pos = pos.relative(context.getClickedFace());
            if (BaseFireBlock.canBePlacedAt(level, pos, context.getHorizontalDirection())) {
                level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
                return true;
            }
        }
        return false;
    }
}
