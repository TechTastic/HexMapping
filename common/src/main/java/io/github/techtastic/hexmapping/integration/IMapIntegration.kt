package io.github.techtastic.hexmapping.integration

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.markers.BaseMarker

interface IMapIntegration {
    fun getMaps(): List<MapIota>

    fun setMarker(world: String, setName: String, marker: BaseMarker)

    fun hasMarker(world: String, setName: String, id: String): Boolean

    fun removeMarker(world: String, setName: String, id: String)

    fun registerPatterns()
}