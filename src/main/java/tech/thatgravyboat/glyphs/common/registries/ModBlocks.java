package tech.thatgravyboat.glyphs.common.registries;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.common.blocks.LightGlyphBlock;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Glyphs.MODID);

    public static final DeferredBlock<LightGlyphBlock> LIGHT = BLOCKS.register("light", () -> new LightGlyphBlock(
            BlockBehaviour.Properties.of()
                    .noCollission()
                    .lightLevel(state -> 15)
                    .strength(0)
                    .noLootTable()
                    .replaceable()
    ));

}
