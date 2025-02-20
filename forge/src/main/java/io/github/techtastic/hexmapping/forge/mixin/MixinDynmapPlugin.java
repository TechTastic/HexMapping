package io.github.techtastic.hexmapping.forge.mixin;

import io.github.techtastic.hexmapping.forge.mixinducks.IForgeDynmapServerGetter;
import io.github.techtastic.hexmapping.mixinducks.IDynmapMapsGetter;
import org.dynmap.DynmapWorld;
import org.dynmap.forge_1_20.DynmapPlugin;
import org.dynmap.forge_1_20.ForgeWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;

@Pseudo
@Mixin(DynmapPlugin.class)
public class MixinDynmapPlugin implements IForgeDynmapServerGetter, IDynmapMapsGetter {
    @Shadow private DynmapPlugin.ForgeServer fserver;

    @Shadow private HashMap<String, ForgeWorld> worlds;

    @Override
    public DynmapPlugin.ForgeServer hexmapping$getServer() {
        return fserver;
    }

    @Override
    public HashMap<String, ? extends DynmapWorld> hexmapping$getWorlds() {
        return worlds;
    }
}
