package tech.thatgravyboat.glyphs.common.network;

import com.teamresourceful.resourcefullib.common.network.Network;
import tech.thatgravyboat.glyphs.Glyphs;
import tech.thatgravyboat.glyphs.common.network.packets.ServerboundFormGlyph;

public class NetworkHandler {

    public static final Network NETWORK = new Network(Glyphs.id("main"), 1);

    public static void init() {
        NETWORK.register(ServerboundFormGlyph.TYPE);
    }
}
