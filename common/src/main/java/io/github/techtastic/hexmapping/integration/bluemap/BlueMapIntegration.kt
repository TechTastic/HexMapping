package io.github.techtastic.hexmapping.integration.bluemap

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.MishapInternalException
import com.flowpowered.math.vector.Vector2d
import com.flowpowered.math.vector.Vector3d
import de.bluecolored.bluemap.api.BlueMapAPI
import de.bluecolored.bluemap.api.markers.ExtrudeMarker
import de.bluecolored.bluemap.api.markers.LineMarker
import de.bluecolored.bluemap.api.markers.Marker
import de.bluecolored.bluemap.api.markers.MarkerSet
import de.bluecolored.bluemap.api.markers.POIMarker
import de.bluecolored.bluemap.api.markers.ShapeMarker
import de.bluecolored.bluemap.api.math.Color
import de.bluecolored.bluemap.api.math.Line
import de.bluecolored.bluemap.api.math.Shape
import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapAPIUninitialized
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapBadMap
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapUnrecognizedMarker
import io.github.techtastic.hexmapping.casting.actions.markers.OpGetMaps
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.integration.IntegrationHelper
import io.github.techtastic.hexmapping.markers.*
import io.github.techtastic.hexmapping.registry.HexMappingPatternRegistry
import kotlin.jvm.optionals.getOrNull
import kotlin.math.max
import kotlin.math.min

object BlueMapIntegration: IMapIntegration {
    private fun getAPI() =
        BlueMapAPI.getInstance().getOrNull() ?: throw MishapAPIUninitialized("bluemap")

    private fun getMapFromWorld(world: String) =
        getAPI().getMap(world).orElseThrow { throw MishapBadMap("bluemap", world) }

    private fun getMarkerSet(world: String, name: String) =
        getMapFromWorld(world).markerSets.getOrPut(name) { MarkerSet(name) }

    override fun getMaps(): List<MapIota> {
        return getAPI().maps.map { map -> MapIota(MapIota.MapDetails("bluemap", map.id)) }
    }

    override fun setMarker(world: String, setName: String, marker: BaseMarker) {
        val set = getMarkerSet(world, setName)

        val bluemapMarker: Marker? = when (marker) {
            is CircleMarker -> ShapeMarker.builder()
                .shape(Shape.createCircle(
                    marker.center.x,
                    marker.center.z,
                    marker.radius,
                    10.0.coerceAtLeast(100.0.coerceAtMost(marker.radius)).toInt()),
                    marker.center.y.toFloat()
                )
                .label(IntegrationHelper.sanitizeHtml(marker.label))
                .lineColor(Color(marker.getLineColor()))
                .fillColor(Color(marker.getFillColor()))
                .lineWidth(marker.getLineWeight().toInt())
                .build()
            is RectangleMarker -> {
                val shape = Shape.createRect(
                    marker.firstCorner.x,
                    marker.firstCorner.z,
                    marker.secondCorner.x,
                    marker.secondCorner.z
                )

                if (marker.firstCorner.y == marker.secondCorner.y)
                    ShapeMarker.builder()
                        .shape(shape, marker.firstCorner.y.toFloat())
                        .label(IntegrationHelper.sanitizeHtml(marker.label))
                        .lineColor(Color(marker.getLineColor()))
                        .fillColor(Color(marker.getFillColor()))
                        .lineWidth(marker.getLineWeight().toInt())
                        .build()
                else
                    ExtrudeMarker.builder()
                        .shape(
                            shape,
                            min(marker.firstCorner.y, marker.secondCorner.y).toFloat(),
                            max(marker.firstCorner.y, marker.secondCorner.y).toFloat()
                        )
                        .centerPosition()
                        .label(IntegrationHelper.sanitizeHtml(marker.label))
                        .lineColor(Color(marker.getLineColor()))
                        .fillColor(Color(marker.getFillColor()))
                        .lineWidth(marker.getLineWeight().toInt())
                        .build()
            }
            is PolygonMarker -> {
                val shape = Shape(marker.points.map { vec -> Vector2d(vec.x, vec.y) })

                if (marker.points.all { p -> p.y == marker.points[0].y })
                    ShapeMarker.builder()
                        .shape(shape, marker.points[0].y.toFloat())
                        .label(IntegrationHelper.sanitizeHtml(marker.label))
                        .lineColor(Color(marker.getLineColor()))
                        .fillColor(Color(marker.getFillColor()))
                        .lineWidth(marker.getLineWeight().toInt())
                        .build()
                else
                    ExtrudeMarker.builder()
                        .shape(
                            shape,
                            marker.points.map { v -> v.y }.stream().min(Double::compareTo).get().toFloat(),
                            marker.points.map { v -> v.y }.stream().max(Double::compareTo).get().toFloat()
                        )
                        .centerPosition()
                        .label(IntegrationHelper.sanitizeHtml(marker.label))
                        .lineColor(Color(marker.getLineColor()))
                        .fillColor(Color(marker.getFillColor()))
                        .lineWidth(marker.getLineWeight().toInt())
                        .build()
            }
            is PolylineMarker -> LineMarker.builder()
                .line(Line.builder().addPoints(marker.points.map { vec -> Vector3d(vec.x, vec.y, vec.z) }).build())
                .label(IntegrationHelper.sanitizeHtml(marker.label))
                .lineColor(Color(marker.getLineColor()))
                .lineWidth(marker.getLineWeight().toInt())
                .build()
            is IconMarker -> POIMarker.builder()
                .label(IntegrationHelper.sanitizeHtml(marker.label))
                .icon(marker.icon, IntegrationHelper.ICON_WIDTH / 2, IntegrationHelper.ICON_HEIGHT / 2)
                .position(marker.pos.x, marker.pos.y, marker.pos.z)
                .build()
            else -> null
        }

        bluemapMarker?.let { set.put(marker.id, it) } ?: throw MishapUnrecognizedMarker("bluemap")
    }

    override fun hasMarker(world: String, setName: String, id: String) =
        getMarkerSet(world, setName).markers.containsKey(id)

    override fun removeMarker(world: String, setName: String, id: String) {
        getMarkerSet(world, setName).remove(id)
    }

    override fun registerPatterns() {
        HexMappingPatternRegistry.register("get_maps/bluemap", ActionRegistryEntry(
            HexPattern.fromAngles("aawwddad", HexDir.SOUTH_WEST),
            OpGetMaps(this::getMaps)
        ))
    }
}