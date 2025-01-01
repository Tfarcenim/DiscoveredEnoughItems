package tfar.dei;

import net.fabricmc.api.ModInitializer;
import tfar.dei.network.PacketHandler;

public class DEIFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        // Use Fabric to bootstrap the Common mod.
        PacketHandler.registerPackets();
        DiscoveredEnoughItems.init();
    }
}
