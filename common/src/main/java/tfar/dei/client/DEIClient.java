package tfar.dei.client;

public class DEIClient {
    public static void joinWorld() {
        DiscoveredItems.setWorldName(DiscoveredItems.getWorldName());
        DiscoveredItems.loadFromDisk();
    }

    public static void leaveWorld() {
        DiscoveredItems.clear();
    }

    /*public static void afterBake() {
        ItemRenderer irenderer = Minecraft.getInstance().getItemRenderer();
        Map<ResourceLocation, BakedModel> registry = irenderer.getItemModelShaper().getModelManager().bakedRegistry;
        DiscoveredItems.HIDDEN = registry.get(DiscoveredItems.ID);
        if (DiscoveredItems.HIDDEN == null) {
            throw new NullPointerException();
        }
    }*/
}
