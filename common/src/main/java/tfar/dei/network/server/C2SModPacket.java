package tfar.dei.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.dei.network.ModPacket;

public interface C2SModPacket<F extends FriendlyByteBuf> extends ModPacket<F> {

    void handleServer(ServerPlayer player);

}
