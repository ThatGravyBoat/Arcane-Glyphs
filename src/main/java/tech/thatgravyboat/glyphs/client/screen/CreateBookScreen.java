package tech.thatgravyboat.glyphs.client.screen;

import com.teamresourceful.resourcefullib.client.screens.BaseCursorScreen;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.olympus.client.components.Widgets;
import earth.terrarium.olympus.client.components.base.renderer.WidgetRenderer;
import earth.terrarium.olympus.client.components.renderers.WidgetRenderers;
import earth.terrarium.olympus.client.constants.MinecraftColors;
import earth.terrarium.olympus.client.layouts.Layouts;
import earth.terrarium.olympus.client.utils.State;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphElement;
import tech.thatgravyboat.glyphs.common.glyphs.registry.GlyphRegistries;
import tech.thatgravyboat.glyphs.common.network.NetworkHandler;
import tech.thatgravyboat.glyphs.common.network.packets.ServerboundFormGlyph;

public class CreateBookScreen extends BaseCursorScreen {

    private static final ResourceLocation BOOK = Glyphs.id("book/book");
    private static final WidgetRenderer<AbstractWidget> ICON_RENDERER = WidgetRenderers.icon(Glyphs.id("book/slot"))
            .withColor(Color.DEFAULT);
    private static final WidgetRenderer<AbstractWidget> SELECTED_ICON_RENDERER = WidgetRenderers.icon(Glyphs.id("book/selected_slot"))
            .withColor(Color.DEFAULT);
    private static final WidgetSprites SPRITES = new WidgetSprites(
            Glyphs.id("book/button"),
            Glyphs.id("book/button_disabled"),
            Glyphs.id("book/button_hovered")
    );

    private static final int WIDTH = 320;
    private static final int HEIGHT = 198;

    private final GlyphElement[] elements = new GlyphElement[18];
    private final State<GlyphElement> element = State.empty();

    private AbstractWidget formButton;

    protected CreateBookScreen(ItemStack stack) {
        super(CommonComponents.EMPTY);
    }

    @Override
    protected void init() {
        int x = (this.width - WIDTH) / 2 + 16;
        int y = (this.height - HEIGHT) / 2 + 16;

        var elementsLayout = Layouts.rows(2).withGap(20);

        for (GlyphElement value : GlyphElement.values()) {
            elementsLayout.withChild(
                    Widgets.button()
                            .withSize(34, 34)
                            .withTexture(null)
                            .withCallback(() -> this.element.set(value))
                            .withRenderer(WidgetRenderers.<AbstractWidget>center(34, 34, (graphics, context, partialTicks) -> {
                                boolean isSelected = this.element.get() == value;
                                (isSelected ? SELECTED_ICON_RENDERER : ICON_RENDERER).render(graphics, context, partialTicks);
                                WidgetRenderers.center(
                                        16, 16,
                                        WidgetRenderers.icon(Glyphs.id("book/elements/" + value.name().toLowerCase()))
                                                .withColor(isSelected ? MinecraftColors.GRAY : MinecraftColors.WHITE)
                                ).render(graphics, context, partialTicks);
                            }))
            );
        }

        elementsLayout.arrangeElements();
        FrameLayout.centerInRectangle(elementsLayout, x, y, 128, 100);
        elementsLayout.build(this::addRenderableWidget);

        this.formButton = addRenderableWidget(
                Widgets.button()
                        .withTexture(SPRITES)
                        .withRenderer(
                                WidgetRenderers.text(Component.literal("Form"))
                                        .withColor(MinecraftColors.GRAY)
                        )
                        .withCallback(() -> {
                            NetworkHandler.NETWORK.sendToServer(new ServerboundFormGlyph(
                                    GlyphRegistries.get(this.elements).id()
                            ));
                            this.onClose();
                        })
                        .withSize(100, 20)
                        .withPosition(x + 14, y + HEIGHT - 62)
                        .asDisabled()
        );

        x += 160;
        y += 4;

        for (int y1 = 0; y1 < 4; y1++) {
            for (int x1 = 0; x1 < 3; x1++) {
                final int index = x1 + y1 * 5;

                addRenderableWidget(
                        Widgets.button()
                                .withSize(16, 18)
                                .withTexture(null)
                                .withCallback(() -> select(index))
                                .withRenderer(WidgetRenderers.<AbstractWidget>center(34, 34, (graphics, context, partialTicks) -> {
                                    GlyphElement element = elements[index];
                                    (context.getWidget().isHovered() ? SELECTED_ICON_RENDERER : ICON_RENDERER).render(graphics, context, partialTicks);
                                    if (element == null) return;
                                    WidgetRenderers.center(
                                            16, 16,
                                            WidgetRenderers.icon(Glyphs.id("book/elements/" + element.name().toLowerCase()))
                                                    .withColor(MinecraftColors.WHITE)
                                    ).render(graphics, context, partialTicks);
                                }))
                                .withPosition(x + 12 + x1 * 38, y + 13 + y1 * 38)
                );
            }
        }

        for (int y1 = 0; y1 < 3; y1++) {
            for (int x1 = 0; x1 < 2; x1++) {
                int index = 3 + x1 + y1 * 5;

                addRenderableWidget(
                        Widgets.button()
                                .withSize(16, 18)
                                .withTexture(null)
                                .withCallback(() -> select(index))
                                .withRenderer(WidgetRenderers.<AbstractWidget>center(34, 34, (graphics, context, partialTicks) -> {
                                    GlyphElement element = elements[index];
                                    (context.getWidget().isHovered() ? SELECTED_ICON_RENDERER : ICON_RENDERER).render(graphics, context, partialTicks);
                                    if (element == null) return;
                                    WidgetRenderers.center(
                                            16, 16,
                                            WidgetRenderers.icon(Glyphs.id("book/elements/" + element.name().toLowerCase()))
                                                    .withColor(MinecraftColors.WHITE)
                                    ).render(graphics, context, partialTicks);
                                }))
                                .withPosition(x + 31 + x1 * 38, y + 32 + y1 * 38)
                );
            }
        }

        checkForm();
    }

    private void select(int index) {
        this.elements[index] = this.elements[index] == this.element.get() ? null : this.element.get();
        checkForm();
    }

    private void checkForm() {
        if (this.formButton == null) return;
        var glyph = GlyphRegistries.get(this.elements);
        this.formButton.active = glyph != null;
        var tooltip = glyph == null ?
                Component.translatable("glyphs.form_glyph_disabled") :
                Component.translatable(
                        "glyphs.form_glyph",
                        Component.translatable(glyph.id().toLanguageKey("glyph"))
                );
        this.formButton.setTooltip(Tooltip.create(tooltip));
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);

        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;

        graphics.blitSprite(BOOK, x, y, WIDTH, HEIGHT);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static void open(ItemStack stack) {
        Minecraft.getInstance().setScreen(new CreateBookScreen(stack));
    }
}
