package io.github.techtastic.hexmapping.integration.squaremap

import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapBadMap
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapUnrecognizedMarker
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.integration.IntegrationHelper
import io.github.techtastic.hexmapping.markers.*
import net.minecraft.server.level.ServerLevel
import xyz.jpenilla.squaremap.api.*
import xyz.jpenilla.squaremap.api.marker.Marker
import xyz.jpenilla.squaremap.api.marker.MarkerOptions
import java.awt.Color

object SquareMapIntegration: IMapIntegration {
    private fun getAPI() = SquaremapProvider.get()

    private fun getWorld(world: String) =
        getAPI().getWorldIfEnabled(WorldIdentifier.parse(world))
            .orElseThrow { MishapBadMap(getModID(), world) }

    private fun getOrCreateLayer(world: String, layerName: String): SimpleLayerProvider {
        val reg = getWorld(world).layerRegistry()
        val key = Key.of(layerName)
        if (reg.hasEntry(key))
            return reg.get(key) as SimpleLayerProvider

        val layer = SimpleLayerProvider
            .builder(layerName)
            .showControls(true)
            .defaultHidden(false)
            .layerPriority(5)
            .zIndex(250)
            .build()
        reg.register(key, layer)

        return layer
    }

    override fun getModID() = "squaremap"

    override fun getMapFromLevel(level: ServerLevel) =
        listOf(MapIota(MapIota.MapDetails(getKeyForIntegration().toString(), level.dimension().location().toString())))

    override fun setMarker(world: String, setName: String, marker: BaseMarker) {
        val layer = getOrCreateLayer(world, setName)

        val squaremapMarker: Marker? = when (marker) {
            is CircleMarker -> Marker.circle(Point.of(marker.center.x, marker.center.z), marker.radius).markerOptions(MarkerOptions.builder()
                .strokeColor(Color(marker.getLineColor()))
                .strokeWeight(marker.getLineWeight().toInt())
                .fillColor(Color(marker.getFillColor()))
                .build()
            )
            is RectangleMarker -> Marker.rectangle(Point.of(marker.firstCorner.x, marker.firstCorner.z), Point.of(marker.secondCorner.x, marker.secondCorner.z)).markerOptions(MarkerOptions.builder()
                .strokeColor(Color(marker.getLineColor()))
                .strokeWeight(marker.getLineWeight().toInt())
                .fillColor(Color(marker.getFillColor()))
                .build()
            )
            is PolygonMarker -> Marker.polygon(marker.points.map { v -> Point.of(v.x, v.z) }).markerOptions(MarkerOptions.builder()
                .strokeColor(Color(marker.getLineColor()))
                .strokeWeight(marker.getLineWeight().toInt())
                .fillColor(Color(marker.getFillColor()))
                .build()
            )
            is PolylineMarker -> Marker.polyline(marker.points.map { v -> Point.of(v.x, v.z) }).markerOptions(MarkerOptions.builder()
                .strokeColor(Color(marker.getLineColor()))
                .strokeWeight(marker.getLineWeight().toInt())
                .build()
            )
            is IconMarker -> Marker.icon(Point.of(marker.pos.x, marker.pos.y), Key.of(marker.icon), IntegrationHelper.ICON_WIDTH, IntegrationHelper.ICON_HEIGHT)
            else -> null
        }

        squaremapMarker?.let { m ->
            layer.addMarker(Key.of(marker.id), m.markerOptions(m.markerOptions().asBuilder()
                .hoverTooltip(marker.id)
                .clickTooltip(IntegrationHelper.sanitizeHtml(marker.label))
                .build()
            ))
        } ?: throw MishapUnrecognizedMarker(getModID())
    }

    override fun hasMarker(world: String, setName: String, id: String): Boolean {
        return getOrCreateLayer(world, setName).hasMarker(Key.of(id))
    }

    override fun removeMarker(world: String, setName: String, id: String) {
        getOrCreateLayer(world, setName).removeMarker(Key.of(id))
    }
}