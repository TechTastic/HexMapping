package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMap
import io.github.techtastic.hexmapping.integration.IntegrationHelper.getMarkerSetOfCaster
import net.minecraft.core.Direction
import ram.talia.moreiotas.api.getString

class OpRemoveMarker : SpellAction {
    override val argc: Int
    get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val world = args.getMap(0, argc)
        val markerKey = args.getString(1, argc)

        return SpellAction.Result(
            Spell(world.getIntegration(), world.mapName, env.getMarkerSetOfCaster(), markerKey),
            0,
            listOf(ParticleSpray(env.mishapSprayPos(), env.mishapSprayPos().relative(Direction.UP, 20.0), 0.0, 0.5))
        )
    }

    class Spell(val integration: IMapIntegration, val world: String, val setName: String, val markerKey: String):
        RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            integration.removeMarker(world, setName, markerKey)
        }
    }
}