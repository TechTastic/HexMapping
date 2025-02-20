package io.github.techtastic.hexmapping.fabric.platform.fabric;

import io.github.techtastic.hexmapping.mixinducks.IDynmapMapsGetter;
import net.minecraft.resources.ResourceLocation;
import org.dynmap.DynmapWorld;

import java.io.InputStream;
import java.util.HashMap;

import static org.dynmap.fabric_1_20.DynmapPlugin.plugin;

public class HexMappingDynmapAbstractionsImpl {
    public static InputStream getIconStream(ResourceLocation icon) {
        if (plugin == null)
            return null;
        return plugin.getFabricServer().openResource(icon.getNamespace(), icon.getPath());
    }

    public static HashMap<String, ? extends DynmapWorld> getMaps() {
        if (plugin == null)
            return null;
        return ((IDynmapMapsGetter) plugin).hexmapping$getWorlds();
    }
}
