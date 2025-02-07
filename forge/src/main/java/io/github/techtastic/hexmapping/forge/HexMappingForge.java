package io.github.techtastic.hexmapping.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.techtastic.hexmapping.HexMapping;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(HexMapping.MOD_ID)
public class HexMappingForge {
    public HexMappingForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(HexMapping.MOD_ID, bus);
        bus.addListener(HexMappingClientForge::init);
        HexMapping.init();
    }
}
