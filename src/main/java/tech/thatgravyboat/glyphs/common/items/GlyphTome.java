package tech.thatgravyboat.glyphs.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.glyphs.api.GlyphType;
import tech.thatgravyboat.glyphs.client.screen.CreateBookScreen;
import tech.thatgravyboat.glyphs.common.registries.ModRegistries;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class GlyphTome extends Item {

    public GlyphTome() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public @NotNull Component getHighlightTip(@NotNull ItemStack item, @NotNull Component displayName) {
        return getGlyph(item)
                .<Component>map(glyph -> Component.translatable(
                        "item.glyphs.tome.with_glyph",
                        Component.translatable(glyph.id().toLanguageKey("glyph")).withStyle(glyph.mainElement().color())
                ))
                .orElse(displayName);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (!tooltip.isEmpty()) {
            tooltip.set(0, getHighlightTip(stack, tooltip.getFirst()));
        } else {
            tooltip.add(getHighlightTip(stack, stack.getDisplayName()));
        }

        getGlyph(stack).ifPresentOrElse(
                glyph -> glyph.addTooltip(tooltip),
                () -> tooltip.add(Component.translatable("glyphs.item.no_glyph").withStyle(ChatFormatting.GRAY))
        );
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        var stack = context.getItemInHand();
        if (tryUseGlyph(stack, context.getPlayer(), glyph -> glyph.use(context))) {
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (getGlyph(stack).isEmpty()) {
            if (hand == InteractionHand.OFF_HAND) return InteractionResultHolder.fail(stack);
            if (level.isClientSide()) {
                CreateBookScreen.open(stack);
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        } else {
            if (tryUseGlyph(stack, player, glyph -> glyph.use(player, hand))) {
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility ability) {
        return getGlyph(stack).map(glyph -> glyph.canPerformAbility(ability)).orElse(false);
    }

    private static boolean tryUseGlyph(ItemStack stack, Player player, Predicate<GlyphType> function) {
        var glyph = getGlyph(stack).orElse(null);
        if (glyph == null) return false;
        if (!checkXp(player, glyph.cost())) return false;
        if (!function.test(glyph)) return false;
        if (player != null && !player.isCreative()) player.giveExperiencePoints(-glyph.cost());
        return true;
    }

    private static boolean checkXp(Player player, int cost) {
        if (player == null) return true;
        if (player.isCreative()) return true;
        if (getXp(player) >= cost) return true;
        if (player instanceof ServerPlayer serverPlayer) serverPlayer.sendSystemMessage(
                Component.translatable("glyphs.not_enough_xp").withStyle(ChatFormatting.BOLD, ChatFormatting.YELLOW),
                true
        );
        return false;
    }

    private static Optional<GlyphType> getGlyph(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModRegistries.GLYPH_DATA));
    }

    private static int getXp(Player player) {
        return (int) (getXpForLevel(player.experienceLevel) + getXpForLevel(player.experienceLevel + 1) * player.experienceProgress);
    }

    private static int getXpForLevel(int level) {
        if (level < 17) {
            return level * level + 6 * level;
        } else if (level < 32) {
            return (int) (2.5 * level * level - 40.5 * level + 360);
        } else {
            return (int) (4.5 * level * level - 162.5 * level + 2220);
        }
    }
}
