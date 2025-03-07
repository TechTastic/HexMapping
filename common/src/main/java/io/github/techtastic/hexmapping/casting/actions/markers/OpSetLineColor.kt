package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import io.github.techtastic.hexmapping.api.casting.iota.MarkerIota
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getColor
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMarker
import io.github.techtastic.hexmapping.markers.*

class OpSetLineColor : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val marker = args.getMarker(0, argc)
        val color = args.getColor(1, argc)

        when (marker) {
            is CircleMarker -> marker.setLineColor(color)
            is RectangleMarker -> marker.setLineColor(color)
            is PolygonMarker -> marker.setLineColor(color)
            is PolylineMarker -> marker.setLineColor(color)
            else -> throw MishapInvalidIota.ofType(MarkerIota(MarkerIota.getType(marker), marker), 0, "marker.options")
        }

        return listOf(MarkerIota(MarkerIota.getType(marker), marker))
    }
}