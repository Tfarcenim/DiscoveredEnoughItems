package tfar.dei.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tfar.dei.DiscoveredEnoughItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.dei.client.DiscoveredItems;

import java.util.List;

@Mixin(ItemRenderer.class)
public class ItemRendererMixinFabric {
    private static final ThreadLocal<ItemStack> local = ThreadLocal.withInitial(() -> ItemStack.EMPTY);

    @Inject(method = "renderQuadList",at = @At("HEAD"))
    private void capture(PoseStack pPoseStack, VertexConsumer pBuffer, List<BakedQuad> pQuads, ItemStack pItemStack, int pCombinedLight, int pCombinedOverlay, CallbackInfo ci) {
        local.set(pItemStack);
    }

    @ModifyArg(method = "renderQuadList",
            at = @At(value = "INVOKE", target = NAME),index = 2)
    private float forceColor0(float o) {
        if (!DiscoveredItems.discovered(local.get())) {
            return 0;
        }
        return o;
    }

    private static final String NAME = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFII)V";

    @ModifyArg(method = "renderQuadList",
            at = @At(value = "INVOKE", target = NAME,remap = false),index = 3)
    private float forceColor1(float o) {
        if (!DiscoveredItems.discovered(local.get())) {
            return 0;
        }
        return o;
    }

    @ModifyArg(method = "renderQuadList",
            at = @At(value = "INVOKE", target = NAME,remap = false),index = 4)
    private float forceColor2(float o) {
        if (!DiscoveredItems.discovered(local.get())) {
            return 0;
        }
        return o;
    }
}