package io.github.techtastic.hexmapping.integration

import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.markers.BaseMarker
import io.github.techtastic.hexmapping.registry.HexMappingIntegrationRegistry
import net.minecraft.server.level.ServerLevel

interface IMapIntegration {
    fun getKeyForIntegration() = HexMappingIntegrationRegistry.getKey(this)

    fun getModID(): String

    fun getMapFromLevel(level: ServerLevel): List<MapIota>

    fun setMarker(world: String, setName: String, marker: BaseMarker)

    fun hasMarker(world: String, setName: String, id: String): Boolean

    fun removeMarker(world: String, setName: String, id: String)
}