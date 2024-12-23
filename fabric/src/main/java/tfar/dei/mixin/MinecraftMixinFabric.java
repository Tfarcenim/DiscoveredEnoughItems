package tfar.dei.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.dei.client.DEIClient;

@Mixin(Minecraft.class)
public class MinecraftMixinFabric {

    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/GameNarrator;clear()V"))
    private void handleLogin(Screen screen, CallbackInfo ci) {
        DEIClient.leaveWorld();
    }
}
