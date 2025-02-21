package io.github.techtastic.hexmapping.registry

import dev.architectury.platform.Platform
import io.github.techtastic.hexmapping.HexMapping.id
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.integration.bluemap.BlueMapIntegration
import io.github.techtastic.hexmapping.integration.dynmap.DynMapIntegration
import io.github.techtastic.hexmapping.integration.pl3xmap.Pl3xMapIntegration
import io.github.techtastic.hexmapping.integration.squaremap.SquareMapIntegration
import net.minecraft.resources.ResourceLocation

object HexMappingIntegrationRegistry {
    private val integrations = mutableMapOf<ResourceLocation, IMapIntegration>()

    init {
        if (Platform.isModLoaded("bluemap"))
            registerIntegration(id("bluemap"), BlueMapIntegration)
        if (Platform.isModLoaded("dynmap"))
            registerIntegration(id("dynmap"), DynMapIntegration)
        if (Platform.isModLoaded("pl3xmap"))
            registerIntegration(id("pl3xmap"), Pl3xMapIntegration)
        if (Platform.isModLoaded("squaremap"))
            registerIntegration(id("squaremap"), SquareMapIntegration)
    }

    fun registerIntegration(key: ResourceLocation, integration: IMapIntegration) {
        integrations[key] = integration
    }

    fun hasIntegration(key: ResourceLocation) =
        integrations.containsKey(key)

    fun getIntegration(key: ResourceLocation) =
        integrations[key]

    fun getKey(integration: IMapIntegration) =
        integrations.filter { (_, int) -> int == integration }.keys.first()

    fun isIntegrationEnabled(key: ResourceLocation) =
        Platform.isModLoaded(getIntegration(key)?.getModID() ?: "")

    fun areAnyIntegrationsEnabled() =
        integrations.keys.any(this::isIntegrationEnabled)

    fun registerPatterns() {
        integrations.values.forEach(IMapIntegration::registerPatterns)
    }
}