package tfar.dei.mixin;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.dei.network.client.S2CItemStackPacket;
import tfar.dei.platform.Services;

import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

@Mixin(InventoryChangeTrigger.class)
public class InventoryChangeTriggerMixinFabric {

    @Unique
    private final WeakHashMap<ServerPlayer,Set<Item>> cache = new WeakHashMap<>();

    @Inject(method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At(value = "HEAD"))
    private void sendPacket(ServerPlayer pPlayer, Inventory pInventory, ItemStack pStack, CallbackInfo ci) {
        cache.computeIfAbsent(pPlayer,player -> new HashSet<>());
        if (!cache.get(pPlayer).contains(pStack.getItem())) {
            Services.PLATFORM.sendToClient(new S2CItemStackPacket(pStack),pPlayer);
            cache.get(pPlayer).add(pStack.getItem());
        }
    }
}
