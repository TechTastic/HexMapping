package io.github.techtastic.hexmapping.forge;

import io.github.techtastic.hexmapping.HexMappingClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Forge client loading entrypoint.
 */
public class HexMappingClientForge {
    public static void init(FMLClientSetupEvent event) {
        HexMappingClient.init();
    }
}
