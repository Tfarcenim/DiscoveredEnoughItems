package tfar.dei;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(DiscoveredEnoughItems.MOD_ID)
public class DEIForge {
    
    public DEIForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        if (FMLEnvironment.dist.isClient()) {
            DEIClient.init(bus);
        }
        DiscoveredEnoughItems.init();
        
    }
}