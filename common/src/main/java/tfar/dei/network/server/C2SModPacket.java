package tfar.dei.network.server;

import net.minecraft.server.level.ServerPlayer;
import tfar.dei.network.ModPacket;

public interface C2SModPacket extends ModPacket {

    void handleServer(ServerPlayer player);

}
