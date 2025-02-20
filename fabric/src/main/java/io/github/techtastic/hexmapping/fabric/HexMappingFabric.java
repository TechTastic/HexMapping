package io.github.techtastic.hexmapping.fabric;

import at.petrak.hexcasting.common.lib.HexRegistries;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import io.github.techtastic.hexmapping.registry.HexMappingIotaTypeRegistry;
import io.github.techtastic.hexmapping.registry.HexMappingPatternRegistry;
import net.fabricmc.api.ModInitializer;
import io.github.techtastic.hexmapping.HexMapping;
import net.minecraft.core.Registry;

import static io.github.techtastic.hexmapping.HexMapping.id;

/**
 * This is your loading entrypoint on fabric(-likes), in case you need to initialize
 * something platform-specific.
 * <br/>
 * Since quilt can load fabric mods, you develop for two platforms in one fell swoop.
 * Feel free to check out the <a href="https://github.com/architectury/architectury-templates">Architectury templates</a>
 * if you want to see how to add quilt-specific code.
 */
public class HexMappingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HexMapping.init();

        HexMappingPatternRegistry.registryCallback((key, entry) -> Registry.register(HexActions.REGISTRY, id(key), entry));
        HexMappingIotaTypeRegistry.registryCallback((key, entry) -> Registry.register(HexIotaTypes.REGISTRY, key, entry));
    }
}
