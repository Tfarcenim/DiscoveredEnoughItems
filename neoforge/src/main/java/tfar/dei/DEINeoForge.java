package tfar.dei;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(DiscoveredEnoughItems.MOD_ID)
public class DEINeoForge {
    
    public DEINeoForge(IEventBus bus, Dist dist, ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT,DEIConfig.CLIENT_SPEC);
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        if (dist.isClient()) {
            DEIClientNeoForge.init(bus);
        }
        DiscoveredEnoughItems.init();
        bus.addListener(PacketHandlerNeoForge::register);
    }
}