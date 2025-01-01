package tfar.dei.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.MixinEnvironment;
import tfar.dei.network.client.S2CModPacket;
import tfar.dei.network.server.C2SModPacket;
import tfar.dei.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <MSG extends S2CModPacket<RegistryFriendlyByteBuf>> void registerClientPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {
        PayloadTypeRegistry.playS2C().register(type,streamCodec);//payload needs to be registered on server/client, packethandler is client only
        if (MixinEnvironment.getCurrentEnvironment().getSide() == MixinEnvironment.Side.CLIENT) {
            ClientPlayNetworking.registerGlobalReceiver(type,(payload, context) -> payload.handleClient());
        }
    }

    @Override
    public <MSG extends C2SModPacket<RegistryFriendlyByteBuf>> void registerServerPlayPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {
        PayloadTypeRegistry.playC2S().register(type, streamCodec);
        ServerPlayNetworking.registerGlobalReceiver(type,(payload, context) ->  payload.handleServer(context.player()));
    }

    @Override
    public void sendToClient(S2CModPacket<?> msg, ServerPlayer player) {
        ServerPlayNetworking.send(player,msg);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        ClientPlayNetworking.send(msg);
    }
}
