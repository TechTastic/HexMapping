package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMap
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMarkerSetOfCaster
import ram.talia.moreiotas.api.getString

class OpHasMarker : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val world = args.getMap(0, argc)
        val markerKey = args.getString(1, argc)

        return listOf(BooleanIota(world.getIntegration().hasMarker(world.mapName, env.getMarkerSetOfCaster(), markerKey)))
    }
}