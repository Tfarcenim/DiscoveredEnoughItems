package tfar.dei.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.dei.client.DEIClient;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixinFabric {
    @Inject(method = "handleLogin", at = @At(value = "RETURN"))
    private void handleLogin(ClientboundLoginPacket packet, CallbackInfo ci) {
        DEIClient.joinWorld();
    }
}
