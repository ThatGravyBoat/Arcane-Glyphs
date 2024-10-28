package tech.thatgravyboat.glyphs.api;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public abstract class GlyphType {

    public abstract ResourceLocation id();

    public abstract List<GlyphElement> elements();

    public abstract GlyphElement mainElement();

    public int cost() {
        return 1;
    }

    public void addTooltip(List<Component> tooltip) {
        tooltip.add(Component.translatable(id().toLanguageKey("glyph", "description")).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(CommonComponents.EMPTY);
        tooltip.add(Component.translatable("glyphs.item.cost").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" ⦿ %d XP".formatted(cost())).withColor(0x80ff20));
    }

    public boolean use(UseOnContext context) {
        return use(context.getPlayer(), context.getHand());
    }

    public boolean use(Player player, InteractionHand hand) {
        return false;
    }

    public boolean canPerformAbility(ItemAbility ability) {
        return false;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof GlyphType other) {
            return id().equals(other.id());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return id().hashCode();
    }
}
