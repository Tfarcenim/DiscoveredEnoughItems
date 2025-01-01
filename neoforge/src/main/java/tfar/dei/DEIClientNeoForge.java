package tfar.dei;

import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.common.NeoForge;
import tfar.dei.client.DEIClient;
import tfar.dei.client.DiscoveredItems;

public class DEIClientNeoForge {

    public static void init(IEventBus bus) {
        NeoForge.EVENT_BUS.addListener(DEIClientNeoForge::tooltips);
        NeoForge.EVENT_BUS.addListener(DEIClientNeoForge::joinWorld);
        NeoForge.EVENT_BUS.addListener(DEIClientNeoForge::leaveWorld);
        bus.addListener(DEIClientNeoForge::afterBake);
        bus.addListener(DEIClientNeoForge::special);
    }

    static void special(ModelEvent.RegisterAdditional event) {
        event.register(DiscoveredItems.ID);
    }

    static void afterBake(ModelEvent.BakingCompleted event) {
        ItemRenderer irenderer = Minecraft.getInstance().getItemRenderer();
        DiscoveredItems.HIDDEN = irenderer.getItemModelShaper().getModelManager().getModel(DiscoveredItems.ID);
    }

    private static void tooltips(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        if (!DiscoveredItems.discovered(stack)) {
            event.getTooltipElements().clear();
            event.getTooltipElements().add(Either.left(Component.literal("???").withStyle(ChatFormatting.WHITE)));
            Component component = DiscoveredItems.tooltip(stack);
            if (component != null) {
                event.getTooltipElements().add(Either.left(component));
            }
        }
    }

    static void joinWorld(ClientPlayerNetworkEvent.LoggingIn event) {
        DEIClient.joinWorld();
    }

    static void leaveWorld(ClientPlayerNetworkEvent.LoggingOut event) {
        DEIClient.leaveWorld();
    }
}
