package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import io.github.techtastic.hexmapping.api.casting.iota.MarkerIota
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getColor
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMarker
import io.github.techtastic.hexmapping.markers.IOptions

class OpSetLineColor : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val marker = args.getMarker(0, argc)
        val color = args.getColor(1, argc)

        if (marker !is IOptions)
            throw MishapInvalidIota.ofType(MarkerIota(MarkerIota.getType(marker), marker), 0, "marker.options")

        if (marker.hasLineColor())
            throw MishapInvalidIota.ofType(MarkerIota(MarkerIota.getType(marker), marker), 0, "marker.options.line")

        marker.setLineColor(color)

        return listOf(MarkerIota(MarkerIota.getType(marker), marker))
    }
}