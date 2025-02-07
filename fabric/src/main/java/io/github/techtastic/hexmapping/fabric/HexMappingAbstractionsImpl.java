package io.github.techtastic.hexmapping.fabric;

import net.fabricmc.loader.api.FabricLoader;
import io.github.techtastic.hexmapping.HexMappingAbstractions;

import java.nio.file.Path;

public class HexMappingAbstractionsImpl {
    /**
     * This is the actual implementation of {@link HexMappingAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
	
    public static void initPlatformSpecific() {
        HexMappingConfigFabric.init();
    }
}
