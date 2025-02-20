package io.github.techtastic.hexmapping.fabric.mixin;

import io.github.techtastic.hexmapping.mixinducks.IDynmapMapsGetter;
import org.dynmap.DynmapWorld;
import org.dynmap.fabric_1_20.DynmapPlugin;
import org.dynmap.fabric_1_20.FabricWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;

@Pseudo
@Mixin(DynmapPlugin.class)
public class MixinDynmapPlugin implements IDynmapMapsGetter {
    @Shadow private HashMap<String, FabricWorld> worlds;

    @Override
    public HashMap<String, ? extends DynmapWorld> hexmapping$getWorlds() {
        return worlds;
    }
}
