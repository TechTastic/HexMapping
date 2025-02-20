package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getInt
import at.petrak.hexcasting.api.casting.getPositiveDouble
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexmapping.api.casting.iota.MarkerIota
import io.github.techtastic.hexmapping.markers.RectangleMarker
import ram.talia.moreiotas.api.getString

class OpCreateRectangleMarker : ConstMediaAction {
    override val argc: Int
        get() = 4

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val id = args.getString(0, argc)
        val label = args.getString(1, argc)
        val firstCorner = args.getVec3(2, argc)
        val secondCorner = args.getVec3(3, argc)

        return listOf(MarkerIota(MarkerIota.Type.RECTANGLE, RectangleMarker(id, label, firstCorner, secondCorner)))
    }
}