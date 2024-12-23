package tfar.dei;

import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.dei.client.DEIClient;
import tfar.dei.client.DiscoveredItems;

import java.util.Map;

public class DEIClientForge {

    public static void init(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(DEIClientForge::tooltips);
        MinecraftForge.EVENT_BUS.addListener(DEIClientForge::joinWorld);
        MinecraftForge.EVENT_BUS.addListener(DEIClientForge::leaveWorld);
        bus.addListener(DEIClientForge::afterBake);
        bus.addListener(DEIClientForge::special);
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
