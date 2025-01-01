package tfar.dei.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface ModPacket<F extends FriendlyByteBuf> extends CustomPacketPayload {
    static  <T extends FriendlyByteBuf,P extends ModPacket<T>> Type<P> type(Class<P> pClass){
        return new Type<>(PacketHandler.packet(pClass));
    }
}
