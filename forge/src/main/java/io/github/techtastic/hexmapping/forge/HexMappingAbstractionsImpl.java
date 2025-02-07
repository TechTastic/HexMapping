package io.github.techtastic.hexmapping.forge;

import io.github.techtastic.hexmapping.HexMappingAbstractions;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class HexMappingAbstractionsImpl {
    /**
     * This is the actual implementation of {@link HexMappingAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
	
    public static void initPlatformSpecific() {
        HexMappingConfigForge.init();
    }
}
