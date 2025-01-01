package tfar.dei.network.client;


import net.minecraft.network.FriendlyByteBuf;
import tfar.dei.network.ModPacket;

public interface S2CModPacket<F extends FriendlyByteBuf> extends ModPacket<F> {

    void handleClient();

}
