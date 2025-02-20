package io.github.techtastic.hexmapping.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import org.dynmap.DynmapWorld;

import java.io.InputStream;
import java.util.HashMap;

public class HexMappingDynmapAbstractions {
    @ExpectPlatform
    public static InputStream getIconStream(ResourceLocation icon) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static HashMap<String, ? extends DynmapWorld> getMaps() {
        throw new AssertionError();
    }
}
