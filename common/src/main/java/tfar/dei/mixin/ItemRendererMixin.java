package tfar.dei.mixin;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tfar.dei.DEIConfig;
import tfar.dei.client.DiscoveredItems;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private BakedModel hideModel(BakedModel i, ItemStack stack) {
        if (!DiscoveredItems.discovered(stack) && DEIConfig.Client.hide_models.get()) {
            return DiscoveredItems.HIDDEN;
        }
        return i;
    }
}