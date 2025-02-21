package io.github.techtastic.hexmapping.integration.dynmap

import io.github.techtastic.hexmapping.platform.HexMappingDynmapAbstractions
import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapAPIUninitialized
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapUnrecognizedMarker
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.integration.IntegrationHelper
import io.github.techtastic.hexmapping.markers.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import org.dynmap.DynmapCommonAPI
import org.dynmap.DynmapCommonAPIListener
import org.dynmap.markers.MarkerSet
import java.awt.Color
import kotlin.math.max
import kotlin.math.min

object DynMapIntegration: DynmapCommonAPIListener(), IMapIntegration {
    var api: DynmapCommonAPI? = null

    override fun apiEnabled(api: DynmapCommonAPI) {
        this.api = api
    }

    private fun getMarkerAPI() = api?.markerAPI
        ?: throw MishapAPIUninitialized(getModID())

    private fun getOrCreateMarkerSet(name: String): MarkerSet {
        val markerAPI = getMarkerAPI()
        return markerAPI.getMarkerSet(name) ?: markerAPI.createMarkerSet(name, name, null, false)
    }

    private fun getOrCreateMarkerIcon(icon: String) =
        getMarkerAPI().getMarkerIcon(icon)
            ?: getMarkerAPI().createMarkerIcon(icon, icon, HexMappingDynmapAbstractions.getIconStream(ResourceLocation.tryParse(icon)))

    override fun getModID() = "dynmap"

    override fun getMapFromLevel(level: ServerLevel) =
        listOf(MapIota(MapIota.MapDetails(getKeyForIntegration().toString(), HexMappingDynmapAbstractions.getWorldName(level))))

    override fun setMarker(world: String, setName: String, marker: BaseMarker) {
        val set = getOrCreateMarkerSet(setName)

        when (marker) {
            is CircleMarker -> {
                set.findCircleMarker(marker.id)?.deleteMarker()
                
                val circle = set.createCircleMarker(
                    marker.id,
                    IntegrationHelper.sanitizeHtml(marker.label),
                    true,
                    world,
                    marker.center.x,
                    marker.center.y,
                    marker.center.z,
                    marker.radius,
                    marker.radius,
                    false
                )
                circle.setLineStyle(marker.getLineWeight().toInt(), Color(marker.getLineColor()).alpha / 255.0, marker.getLineColor())
                circle.setFillStyle(Color(marker.getLineColor()).alpha / 255.0, marker.getFillColor())

                circle
            }
            is RectangleMarker -> {
                set.findAreaMarker(marker.id)?.deleteMarker()
                
                val rect = set.createAreaMarker(
                    marker.id,
                    IntegrationHelper.sanitizeHtml(marker.label),
                    true, 
                    world, 
                    doubleArrayOf(marker.firstCorner.x, marker.secondCorner.x), 
                    doubleArrayOf(marker.firstCorner.z, marker.secondCorner.z), 
                    false
                )
                
                rect.setRangeY(max(marker.firstCorner.y, marker.secondCorner.y), min(marker.firstCorner.y, marker.secondCorner.y))
                rect.setLineStyle(marker.getLineWeight().toInt(), Color(marker.getLineColor()).alpha / 255.0, marker.getLineColor())
                rect.setFillStyle(Color(marker.getLineColor()).alpha / 255.0, marker.getFillColor())

                rect
            }
            is PolygonMarker -> {
                set.findAreaMarker(marker.id)?.deleteMarker()
                
                val area = set.createAreaMarker(
                    marker.id,
                    IntegrationHelper.sanitizeHtml(marker.label),
                    true, 
                    world, 
                    marker.points.map { vec -> vec.x }.toDoubleArray(), 
                    marker.points.map { vec -> vec.z }.toDoubleArray(), 
                    false
                )
                
                area.setRangeY(
                    marker.points.map { v -> v.y }.stream().max(Double::compareTo).get(),
                    marker.points.map { v -> v.y }.stream().min(Double::compareTo).get()
                )
                area.setLineStyle(marker.getLineWeight().toInt(), Color(marker.getLineColor()).alpha / 255.0, marker.getLineColor())
                area.setFillStyle(Color(marker.getLineColor()).alpha / 255.0, marker.getFillColor())

                area
            }
            is PolylineMarker -> {
                set.findPolyLineMarker(marker.id)?.deleteMarker()
                
                val line = set.createPolyLineMarker(
                    marker.id,
                    IntegrationHelper.sanitizeHtml(marker.label),
                    true,
                    world, 
                    marker.points.map { vec -> vec.x }.toDoubleArray(),
                    marker.points.map { vec -> vec.y }.toDoubleArray(),
                    marker.points.map { vec -> vec.z }.toDoubleArray(),
                    false
                )
                
                line.setLineStyle(marker.getLineWeight().toInt(), Color(marker.getLineColor()).alpha / 255.0, marker.getLineColor())

                line
            }
            is IconMarker -> {
                set.findMarker(marker.id)?.deleteMarker()
                
                set.createMarker(
                    marker.id,
                    IntegrationHelper.sanitizeHtml(marker.label),
                    true,
                    world,
                    marker.pos.x,
                    marker.pos.y,
                    marker.pos.z,
                    getOrCreateMarkerIcon(marker.icon),
                    false
                )
            }
            else -> null
        } ?: throw MishapUnrecognizedMarker(getModID())
    }

    override fun hasMarker(world: String, setName: String, id: String): Boolean {
        val set = getOrCreateMarkerSet(setName)

        return set.findMarker(id)?.world == world ||
                set.findPolyLineMarker(id)?.world == world ||
                set.findAreaMarker(id)?.world == world ||
                set.findCircleMarker(id)?.world == world
    }

    override fun removeMarker(world: String, setName: String, id: String) {
        val set = getOrCreateMarkerSet(setName)

        set.findMarker(id)?.deleteMarker()
        set.findPolyLineMarker(id)?.deleteMarker()
        set.findAreaMarker(id)?.deleteMarker()
        set.findCircleMarker(id)?.deleteMarker()
    }
}