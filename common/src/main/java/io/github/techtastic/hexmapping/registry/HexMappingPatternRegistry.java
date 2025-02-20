package io.github.techtastic.hexmapping.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import dev.architectury.platform.Platform;
import io.github.techtastic.hexmapping.casting.actions.markers.*;
import io.github.techtastic.hexmapping.integration.bluemap.BlueMapIntegration;
import io.github.techtastic.hexmapping.integration.dynmap.DynMapIntegration;
import io.github.techtastic.hexmapping.integration.pl3xmap.Pl3xMapIntegration;
import io.github.techtastic.hexmapping.integration.squaremap.SquareMapIntegration;

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
            new ActionRegistryEntry(HexPattern.fromAngles("aawe", HexDir.SOUTH_WEST), new OpHasMarker()));
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

    public static void init() {
        if (Platform.isModLoaded("bluemap"))
            BlueMapIntegration.INSTANCE.registerPatterns();
        if (Platform.isModLoaded("dynmap"))
            DynMapIntegration.INSTANCE.registerPatterns();
        if (Platform.isModLoaded("pl3xmap"))
            Pl3xMapIntegration.INSTANCE.registerPatterns();
        if (Platform.isModLoaded("squaremap"))
            SquareMapIntegration.INSTANCE.registerPatterns();
    }

    public static ActionRegistryEntry register(String name, ActionRegistryEntry entry) {
        return ACTIONS.put(name, entry);
    }

    public static void registryCallback(BiConsumer<String, ActionRegistryEntry> callback) {
        ACTIONS.forEach(callback);
    }
}
