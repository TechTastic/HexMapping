package io.github.techtastic.hexmapping.integration.pl3xmap

import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapBadMap
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapUnrecognizedMarker
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.integration.IntegrationHelper
import io.github.techtastic.hexmapping.markers.*
import net.minecraft.server.level.ServerLevel
import net.pl3x.map.core.Pl3xMap
import net.pl3x.map.core.markers.Point
import net.pl3x.map.core.markers.layer.SimpleLayer
import net.pl3x.map.core.markers.marker.Marker
import net.pl3x.map.core.markers.option.Options

object Pl3xMapIntegration: IMapIntegration {
    private fun getAPI() =
        Pl3xMap.api()

    private fun getWorld(world: String) =
        getAPI().worldRegistry.get(world)
            ?: throw MishapBadMap(getModID(), world)

    private fun getLayerInWorld(world: String, layerName: String) =
        getWorld(world).layerRegistry.get(layerName)
            ?: getWorld(world).layerRegistry.register(layerName, SimpleLayer(layerName) { layerName })

    override fun getModID() = "pl3xmap"

    override fun getMapFromLevel(level: ServerLevel) = getAPI().worldRegistry.filter { world ->
        world.getLevel<ServerLevel>() == level }.map { world ->
        MapIota(MapIota.MapDetails(getKeyForIntegration().toString(), world.key)) }

    override fun setMarker(world: String, setName: String, marker: BaseMarker) {
        val layer = getLayerInWorld(world, setName)
        val pl3xmapMarker: Marker<*>? = when (marker) {
            is CircleMarker -> Marker.circle(marker.id, marker.center.x, marker.center.z, marker.radius).setOptions(Options.builder()
                .strokeColor(marker.getLineColor())
                .strokeWeight(marker.getLineWeight().toInt())
                .fillColor(marker.getFillColor())
                .build()
            )
            is RectangleMarker -> Marker.rectangle(marker.id, marker.firstCorner.x, marker.firstCorner.z, marker.secondCorner.x, marker.secondCorner.z).setOptions(Options.builder()
                .strokeColor(marker.getLineColor())
                .strokeWeight(marker.getLineWeight().toInt())
                .fillColor(marker.getFillColor())
                .build()
            )
            is PolygonMarker -> Marker.polygon(marker.id, Marker.polyline(marker.id, marker.points.map { v -> Point.of(v.x, v.z) })).setOptions(Options.builder()
                .strokeColor(marker.getLineColor())
                .strokeWeight(marker.getLineWeight().toInt())
                .fillColor(marker.getFillColor())
                .build()
            )
            is PolylineMarker -> Marker.polyline(marker.id, marker.points.map { v -> Point.of(v.x, v.z) }).setOptions(Options.builder()
                .strokeColor(marker.getLineColor())
                .strokeWeight(marker.getLineWeight().toInt())
                .build()
            )
            is IconMarker -> Marker.icon(marker.id, marker.pos.x, marker.pos.z, marker.icon)
            else -> null
        }

        pl3xmapMarker?.setPane(IntegrationHelper.sanitizeHtml(marker.label))?.let(layer.markers::add)
            ?: MishapUnrecognizedMarker(getModID())
    }

    override fun hasMarker(world: String, setName: String, id: String): Boolean {
        return getLayerInWorld(world, setName).markers.find { marker -> marker.key == id } != null
    }

    override fun removeMarker(world: String, setName: String, id: String) {
        getLayerInWorld(world, setName).markers.removeIf { marker -> marker.key == id }
    }
}