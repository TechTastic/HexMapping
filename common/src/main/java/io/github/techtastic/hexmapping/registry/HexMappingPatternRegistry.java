package io.github.techtastic.hexmapping.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import dev.architectury.platform.Platform;
import io.github.techtastic.hexmapping.casting.actions.markers.*;
import io.github.techtastic.hexmapping.casting.actions.markers.maps.OpGetBluemapMaps;
import io.github.techtastic.hexmapping.casting.actions.markers.maps.OpGetDynmapMaps;
import io.github.techtastic.hexmapping.casting.actions.markers.maps.OpGetPl3xmapMaps;
import io.github.techtastic.hexmapping.casting.actions.markers.maps.OpGetSquaremapMaps;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HexMappingPatternRegistry {
    private static final Map<String, ActionRegistryEntry> ACTIONS = new HashMap<>();

    public static ActionRegistryEntry CREATE_MARKER$CIRCLE = register("create_marker/circle",
            new ActionRegistryEntry(HexPattern.fromAngles("aaweeqqqqq", HexDir.SOUTH_WEST), new OpCreateCircleMarker()));
    public static ActionRegistryEntry CREATE_MARKER$RECTANGLE = register("create_marker/rectangle",
            new ActionRegistryEntry(HexPattern.fromAngles("aaweeqwaqw", HexDir.SOUTH_WEST), new OpCreateRectangleMarker()));
    public static ActionRegistryEntry CREATE_MARKER$POLYGON = register("create_marker/polygon",
            new ActionRegistryEntry(HexPattern.fromAngles("aaweeaeawaea", HexDir.SOUTH_WEST), new OpCreatePolygonMarker()));
    public static ActionRegistryEntry CREATE_MARKER$POLYLINE = register("create_marker/polyline",
            new ActionRegistryEntry(HexPattern.fromAngles("aaweqde", HexDir.SOUTH_WEST), new OpCreatePolylineMarker()));
    public static ActionRegistryEntry CREATE_MARKER$ICON = register("create_marker/icon",
            new ActionRegistryEntry(HexPattern.fromAngles("aaweeqqaed", HexDir.SOUTH_WEST), new OpCreateIconMarker()));

    public static ActionRegistryEntry HAS_MARKER = register("has_marker",
            new ActionRegistryEntry(HexPattern.fromAngles("aawda", HexDir.SOUTH_WEST), new OpHasMarker()));
    public static ActionRegistryEntry SET_FILL_COLOR = register("set_fill_color",
            new ActionRegistryEntry(HexPattern.fromAngles("aawea", HexDir.SOUTH_WEST), new OpSetFillColor()));
    public static ActionRegistryEntry SET_LINE_COLOR = register("set_line_color",
            new ActionRegistryEntry(HexPattern.fromAngles("aawee", HexDir.SOUTH_WEST), new OpSetLineColor()));
    public static ActionRegistryEntry SET_LINE_WEIGHT = register("set_line_weight",
            new ActionRegistryEntry(HexPattern.fromAngles("aaweed", HexDir.SOUTH_WEST), new OpSetLineWeight()));

    public static ActionRegistryEntry SET_MARKER = register("set_marker",
            new ActionRegistryEntry(HexPattern.fromAngles("aawwaawqaawwaaw", HexDir.NORTH_EAST), new OpSetMarker()));
    public static ActionRegistryEntry REMOVE_MARKER = register("remove_marker",
            new ActionRegistryEntry(HexPattern.fromAngles("wddweddwwddwedd", HexDir.NORTH_EAST), new OpRemoveMarker()));

    public static ActionRegistryEntry GET_MAPS$BLUEMAP = Platform.isModLoaded("bluemap") ?
            register("get_maps/bluemap", new ActionRegistryEntry(HexPattern.fromAngles("aawwddad",
                    HexDir.SOUTH_WEST), new OpGetBluemapMaps())) : null;
    public static ActionRegistryEntry GET_MAPS$DYNMAP = Platform.isModLoaded("dynmap") ?
            register("get_maps/dynmap", new ActionRegistryEntry(HexPattern.fromAngles("aawwdee",
                    HexDir.SOUTH_WEST), new OpGetDynmapMaps())) : null;
    public static ActionRegistryEntry GET_MAPS$PL3XMAP = Platform.isModLoaded("pl3xmap") ?
            register("get_maps/pl3xmap", new ActionRegistryEntry(HexPattern.fromAngles("aawwdd",
                    HexDir.SOUTH_WEST), new OpGetPl3xmapMaps())) : null;
    public static ActionRegistryEntry GET_MAPS$SQAUREMAP = Platform.isModLoaded("squaremap") ?
            register("get_maps/squaremap", new ActionRegistryEntry(HexPattern.fromAngles("aaeqwawqw",
                    HexDir.SOUTH_WEST), new OpGetSquaremapMaps())) : null;

    public static void init() {
        HexMappingIntegrationRegistry.INSTANCE.registerPatterns();
    }

    public static ActionRegistryEntry register(String name, ActionRegistryEntry entry) {
        return ACTIONS.put(name, entry);
    }

    public static void registryCallback(BiConsumer<String, ActionRegistryEntry> callback) {
        ACTIONS.forEach(callback);
    }
}
