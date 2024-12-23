package tfar.dei.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tfar.dei.client.DiscoveredItems;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
    @ModifyVariable(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"), argsOnly = true)
    private String question(String s, Font $$0, ItemStack stack) {
        if (!DiscoveredItems.discovered(stack)) {
            return "?";
        }
        return s;
    }
}
