package tfar.dei.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.dei.network.client.S2CItemStackPacket;
import tfar.dei.platform.Services;

import java.util.HashSet;
import java.util.Set;

@Mixin(targets = {"net/minecraft/server/level/ServerPlayer$2"})
public class ServerPlayerMixinForge {//BROKEN, DON'T USE
   // @Shadow(remap = false)
    @Final
    ServerPlayer this$0;

    //don't send unneeded packets
    private final Set<Item> cache = new HashSet<>();

    @Inject(method = "slotChanged",at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/InventoryChangeTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V"))
    private void sendPacket(AbstractContainerMenu p_143466_, int p_143467_, ItemStack stack, CallbackInfo ci) {
        if (!cache.contains(stack.getItem())) {
            Services.PLATFORM.sendToClient(new S2CItemStackPacket(stack), this.this$0);
            cache.add(stack.getItem());
        }
    }
}
