package tech.thatgravyboat.glyphs.common.glyphs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.api.GlyphElement;
import tech.thatgravyboat.glyphs.api.GlyphType;

import java.util.List;

public class FireBallGlyph extends GlyphType {

    @Override
    public ResourceLocation id() {
        return Glyphs.id("fireball");
    }

    @Override
    public List<GlyphElement> elements() {
        return List.of(
                GlyphElement.AIR,
                GlyphElement.FIRE,
                GlyphElement.AIR,
                GlyphElement.FIRE
        );
    }

    @Override
    public GlyphElement mainElement() {
        return GlyphElement.FIRE;
    }

    @Override
    public int cost() {
        return 2;
    }

    @Override
    public boolean use(Player player, InteractionHand hand) {
        SmallFireball fireball = new SmallFireball(
                player.level(),
                player.getX(), player.getEyeY() - 0.01, player.getZ(),
                Vec3.ZERO
        );
        fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F, 0.0F);
        player.level().addFreshEntity(fireball);
        return true;
    }
}
