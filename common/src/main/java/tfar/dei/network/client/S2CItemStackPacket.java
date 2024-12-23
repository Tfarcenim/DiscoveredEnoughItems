package tfar.dei.network.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import tfar.dei.client.DiscoveredItems;

public record S2CItemStackPacket(ItemStack stack) implements S2CModPacket {

    public S2CItemStackPacket(FriendlyByteBuf buf) {
        this(buf.readItem());
    }

    @Override
    public void handleClient() {
        DiscoveredItems.addDiscovered(stack);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeItem(stack);
    }
}
