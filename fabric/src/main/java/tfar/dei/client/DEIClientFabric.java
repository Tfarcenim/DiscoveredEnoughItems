package tfar.dei.client;

import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.fml.config.ModConfig;
import tfar.dei.DEIConfig;
import tfar.dei.DiscoveredEnoughItems;

import java.util.List;
import java.util.Map;

public class DEIClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(pluginContext -> pluginContext.addModels(DiscoveredItems.ID.id()));
        ItemTooltipCallback.EVENT.register(this::tooltip);
        NeoForgeConfigRegistry.INSTANCE.register(DiscoveredEnoughItems.MOD_ID, ModConfig.Type.CLIENT, DEIConfig.CLIENT_SPEC);
    }

    private void tooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag tooltipFlag, List<Component> list) {
        if (!DiscoveredItems.discovered(stack)) {
            list.clear();
            list.add(Component.literal("???").withStyle(ChatFormatting.WHITE));
            Component component = DiscoveredItems.tooltip(stack);
            if (component != null) {
                list.add(component);
            }
        }
    }

    public static void afterBake() {
        ItemRenderer irenderer = Minecraft.getInstance().getItemRenderer();
        Map<ResourceLocation, BakedModel> registry = irenderer.getItemModelShaper().getModelManager().bakedRegistry;
        DiscoveredItems.HIDDEN = registry.get(DiscoveredItems.ID);
        if (DiscoveredItems.HIDDEN == null) {
            throw new NullPointerException();
        }
    }

}
