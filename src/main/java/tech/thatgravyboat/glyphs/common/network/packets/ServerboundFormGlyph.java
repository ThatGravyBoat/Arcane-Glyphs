package tech.thatgravyboat.glyphs.common.network.packets;

import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphType;
import tech.thatgravyboat.glyphs.common.glyphs.registry.GlyphRegistries;
import tech.thatgravyboat.glyphs.common.registries.ModRegistries;

import java.util.function.Consumer;

public record ServerboundFormGlyph(ResourceLocation id) implements Packet<ServerboundFormGlyph> {

    public static final ServerboundPacketType<ServerboundFormGlyph> TYPE = new Type();

    @Override
    public PacketType<ServerboundFormGlyph> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType.Server<ServerboundFormGlyph> {

        public Type() {
            super(
                    Glyphs.id("form_glyph"),
                    ExtraByteCodecs.RESOURCE_LOCATION.map(ServerboundFormGlyph::new, ServerboundFormGlyph::id)
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundFormGlyph message) {
            return player -> {
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (stack.is(ModRegistries.TOME)) {
                    GlyphType type = GlyphRegistries.get(message.id());
                    if (type == null) return;
                    stack.set(ModRegistries.GLYPH_DATA, type);
                    player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                }
            };
        }
    }
}
