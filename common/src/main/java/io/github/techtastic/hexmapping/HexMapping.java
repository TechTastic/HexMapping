package io.github.techtastic.hexmapping;

import io.github.techtastic.hexmapping.registry.HexMappingIotaTypeRegistry;
import io.github.techtastic.hexmapping.registry.HexMappingItemRegistry;
import io.github.techtastic.hexmapping.registry.HexMappingPatternRegistry;
import io.github.techtastic.hexmapping.networking.HexMappingNetworking;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class HexMapping {
    public static final String MOD_ID = "hexmapping";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("HexMapping says hello!");

        HexMappingAbstractions.initPlatformSpecific();
        HexMappingItemRegistry.init();
        HexMappingIotaTypeRegistry.init();
        HexMappingPatternRegistry.init();
		HexMappingNetworking.init();

        LOGGER.info(HexMappingAbstractions.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    /**
     * Shortcut for identifiers specific to this mod.
     */
    public static ResourceLocation id(String string) {
        return new ResourceLocation(MOD_ID, string);
    }
}
