package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getInt
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getPositiveDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import io.github.techtastic.hexmapping.api.casting.iota.MarkerIota
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getPoints
import io.github.techtastic.hexmapping.markers.PolylineMarker
import ram.talia.moreiotas.api.getString

class OpCreatePolylineMarker : ConstMediaAction {
    override val argc: Int
        get() = 3

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val id = args.getString(0, argc)
        val label = args.getString(1, argc)
        val points = args.getPoints(2, argc)

        return listOf(MarkerIota(MarkerIota.Type.POLYLINE, PolylineMarker(id, label, points)))
    }
}