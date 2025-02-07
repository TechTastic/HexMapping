package io.github.techtastic.hexmapping.fabric;

import net.fabricmc.api.ClientModInitializer;
import io.github.techtastic.hexmapping.HexMappingClient;

/**
 * Fabric client loading entrypoint.
 */
public class HexMappingClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HexMappingClient.init();
    }
}
