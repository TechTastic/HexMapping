package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMap
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMarker
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMarkerSetOfCaster
import io.github.techtastic.hexmapping.markers.*
import net.minecraft.core.Direction

class OpSetMarker : SpellAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val world = args.getMap(0, argc)
        val marker = args.getMarker(1, argc)

        when (marker) {
            is CircleMarker -> {
                env.assertVecInRange(marker.center)
                env.assertVecInRange(marker.center.relative(Direction.NORTH, marker.radius))
                env.assertVecInRange(marker.center.relative(Direction.SOUTH, marker.radius))
                env.assertVecInRange(marker.center.relative(Direction.EAST, marker.radius))
                env.assertVecInRange(marker.center.relative(Direction.WEST, marker.radius))
            }
            is RectangleMarker -> {
                env.assertVecInRange(marker.firstCorner)
                env.assertVecInRange(marker.secondCorner)
            }
            is PolygonMarker -> marker.points.forEach(env::assertVecInRange)
            is PolylineMarker -> marker.points.forEach(env::assertVecInRange)
            is IconMarker -> env.assertVecInRange(marker.pos)
        }

        return SpellAction.Result(
            Spell(world, env.getMarkerSetOfCaster(), marker),
            10 * MediaConstants.CRYSTAL_UNIT,
            listOf(ParticleSpray(env.mishapSprayPos(), env.mishapSprayPos().relative(Direction.UP, 20.0), 0.0, 0.5))
        )
    }

    class Spell(val world: MapIota.MapDetails, val setName: String, val marker: BaseMarker): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            world.getIntegration().setMarker(world.mapName, setName, marker)
        }
    }
}