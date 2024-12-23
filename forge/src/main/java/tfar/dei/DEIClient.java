package tfar.dei;

import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.dei.client.DiscoveredItems;

public class DEIClient {

    public static void init(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(DEIClient::tooltips);
        MinecraftForge.EVENT_BUS.addListener(DEIClient::joinWorld);
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
        DiscoveredItems.setWorldName(getWorldName());
        DiscoveredItems.loadFromDisk();
    }

    public static String getWorldName() {
        Minecraft minecraft = Minecraft.getInstance();
        ServerData serverData = minecraft.getCurrentServer();
        if (serverData != null) {
            return serverData.name;
        } else {
            IntegratedServer integratedServer = minecraft.getSingleplayerServer();
            return integratedServer.getMotd();
        }
    }
}
