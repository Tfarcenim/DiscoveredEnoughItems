package tfar.dei.mixin;

import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.dei.client.DEIClientFabric;

@Mixin(ModelManager.class)
public class ModelManagerMixinFabric {
    @Inject(method = "apply",at = @At("RETURN"))
    private void cacheModel(@Coerce Object reloadState, ProfilerFiller profiler, CallbackInfo ci) {
        DEIClientFabric.afterBake();
    }
}
