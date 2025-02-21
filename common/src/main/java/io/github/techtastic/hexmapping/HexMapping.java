package io.github.techtastic.hexmapping;

import com.samsthenerd.inline.api.InlineAPI;
import com.samsthenerd.inline.api.data.SpriteInlineData;
import com.samsthenerd.inline.api.matching.*;
import com.samsthenerd.inline.utils.TextureSprite;
import dev.architectury.platform.Platform;
import io.github.techtastic.hexmapping.registry.HexMappingIntegrationRegistry;
import io.github.techtastic.hexmapping.registry.HexMappingIotaTypeRegistry;
import io.github.techtastic.hexmapping.registry.HexMappingPatternRegistry;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class HexMapping {
    public static final String MOD_ID = "hexmapping";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("HexMapping says hello!");

        HexMappingIotaTypeRegistry.init();
        HexMappingPatternRegistry.init();

        PatchouliAPI.get().setConfigFlag("has_map_mod",
                HexMappingIntegrationRegistry.INSTANCE.areAnyIntegrationsEnabled());
    }

    /**
     * Shortcut for identifiers specific to this mod.
     */
    public static ResourceLocation id(String string) {
        return new ResourceLocation(MOD_ID, string);
    }
}
