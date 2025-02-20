package io.github.techtastic.hexmapping.mixinducks;

import org.dynmap.DynmapWorld;

import java.util.HashMap;

public interface IDynmapMapsGetter {
    HashMap<String, ? extends DynmapWorld> hexmapping$getWorlds();
}
