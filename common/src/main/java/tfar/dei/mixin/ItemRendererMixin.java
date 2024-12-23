package tfar.dei.mixin;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tfar.dei.client.DiscoveredItems;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), ordinal = 0, argsOnly = true)//light,overlay
    private int blackout(int i, ItemStack stack) {
        if (!DiscoveredItems.discovered(stack)) {
            return DiscoveredItems.packed();
        }
        return i;
    }

    @ModifyVariable(method = "render", at = @At("HEAD"), ordinal = 1, argsOnly = true)//light,overlay
    private int blackout1(int i, ItemStack stack) {
        if (!DiscoveredItems.discovered(stack)) {
            return DiscoveredItems.overlay();
        }
        return i;
    }



}