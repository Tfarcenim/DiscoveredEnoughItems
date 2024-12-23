package tfar.dei.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.dei.client.DiscoveredItems;

import java.util.List;

@Mixin(ItemRenderer.class)
//@Debug(export = true)
public class ItemRendererMixinForge {

    private static final ThreadLocal<ItemStack> local = ThreadLocal.withInitial(() -> ItemStack.EMPTY);

    @Inject(method = "renderQuadList",at = @At("HEAD"))
    private void capture(PoseStack pPoseStack, VertexConsumer pBuffer, List<BakedQuad> pQuads, ItemStack pItemStack, int pCombinedLight, int pCombinedOverlay, CallbackInfo ci) {
        local.set(pItemStack);
    }

    @ModifyArg(method = "renderQuadList",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFIIZ)V",remap = false),index = 2)
    private float forceColor0(float o) {
        if (!DiscoveredItems.discovered(local.get())) {
            return 0;
        }
        return o;
    }

    @ModifyArg(method = "renderQuadList",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFIIZ)V",remap = false),index = 3)
    private float forceColor1(float o) {
        if (!DiscoveredItems.discovered(local.get())) {
            return 0;
        }
        return o;
    }

    @ModifyArg(method = "renderQuadList",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFIIZ)V",remap = false),index = 4)
    private float forceColor2(float o) {
        if (!DiscoveredItems.discovered(local.get())) {
            return 0;
        }
        return o;
    }
}