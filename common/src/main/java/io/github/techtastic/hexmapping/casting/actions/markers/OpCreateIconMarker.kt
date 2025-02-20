package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexmapping.api.casting.iota.MarkerIota
import io.github.techtastic.hexmapping.markers.IconMarker
import ram.talia.moreiotas.api.getString

class OpCreateIconMarker : ConstMediaAction {
    override val argc: Int
        get() = 4

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val id = args.getString(0, argc)
        val label = args.getString(1, argc)
        val iconAddress = args.getString(2, argc)
        val pos = args.getVec3(3, argc)

        return listOf(MarkerIota(MarkerIota.Type.ICON, IconMarker(id, label, iconAddress, pos)))
    }
}