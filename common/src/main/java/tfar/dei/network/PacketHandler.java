package tfar.dei.network;

import net.minecraft.resources.ResourceLocation;
import tfar.dei.DiscoveredEnoughItems;
import tfar.dei.network.client.S2CItemStackPacket;
import tfar.dei.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerClientPacket(S2CItemStackPacket.class, S2CItemStackPacket::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return DiscoveredEnoughItems.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
