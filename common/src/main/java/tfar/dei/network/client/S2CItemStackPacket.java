package tfar.dei.network.client;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import tfar.dei.client.DiscoveredItems;
import tfar.dei.network.ModPacket;

public record S2CItemStackPacket(ItemStack stack) implements S2CModPacket<RegistryFriendlyByteBuf> {

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CItemStackPacket> STREAM_CODEC =
           StreamCodec.composite(ItemStack.STREAM_CODEC,S2CItemStackPacket::stack,S2CItemStackPacket::new);


    public static final Type<S2CItemStackPacket> TYPE = ModPacket.type(S2CItemStackPacket.class);

    @Override
    public void handleClient() {
        DiscoveredItems.addDiscovered(stack);
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
