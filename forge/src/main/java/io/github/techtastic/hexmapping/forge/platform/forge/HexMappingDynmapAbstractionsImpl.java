package io.github.techtastic.hexmapping.forge.platform.forge;

import io.github.techtastic.hexmapping.forge.mixinducks.IForgeDynmapServerGetter;
import io.github.techtastic.hexmapping.mixinducks.IDynmapMapsGetter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.dynmap.DynmapWorld;
import org.dynmap.forge_1_20.ForgeWorld;

import java.io.InputStream;
import java.util.HashMap;

import static org.dynmap.forge_1_20.DynmapPlugin.plugin;

public class HexMappingDynmapAbstractionsImpl {
    public static InputStream getIconStream(ResourceLocation icon) {
        return ((IForgeDynmapServerGetter) plugin).hexmapping$getServer().openResource(icon.getNamespace(), icon.getPath());
    }

    public static HashMap<String, ? extends DynmapWorld> getMaps() {
        return ((IDynmapMapsGetter) plugin).hexmapping$getWorlds();
    }

    public static String getWorldName(ServerLevel level) {
        return ForgeWorld.getWorldName(level);
    }
}
