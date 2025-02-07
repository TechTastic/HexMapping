package io.github.techtastic.hexmapping.casting.patterns.spells

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadEntity
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.server.level.ServerPlayer
import net.minecraft.network.chat.Component
import io.github.techtastic.hexmapping.networking.HexMappingNetworking
import io.github.techtastic.hexmapping.networking.SetLookPitchS2CMsg

class OpCongrats : SpellAction {
    /**
     * The number of arguments from the stack that this action requires.
     */
    override val argc = 1
    val cost = 2 * MediaConstants.DUST_UNIT

    /**
     * The method called when this Action is actually executed. Accepts the [args]
     * that were on the stack (there will be [argc] of them), and the [ctx],
     * which contains things like references to the caster, the ServerLevel,
     * methods to determine whether locations and entities are in ambit, etc.
     * Returns a triple of things. The [RenderedSpell] is responsible for the spell actually
     * doing things in the world, the [Int] is how much media the spell should cost,
     * and the [List] of [ParticleSpray] renders particle effects for the result of the SpellAction.
     *
     * The [execute] method should only contain code to find the targets of the spell and validate
     * them. All the code that actually makes changes to the world (breaking blocks, teleporting things,
     * etc.) should be in the private [Spell] data class below.
     */
    override fun execute(args: List<Iota>, ctx: CastingEnvironment): SpellAction.Result {
        val target = args.getEntity(0, argc)

        // makes sure that the target player is inside the range
        // the caster is allowed to affect.
        ctx.assertEntityInRange(target)

        if (target !is ServerPlayer) throw MishapBadEntity(
            target,
            Component.translatable("text.hexmapping.congrats.player")
        )

        return SpellAction.Result(
            Spell(target),
            cost,
            listOf(ParticleSpray.burst(target.position(), 1.0))
        )
    }

    /**
     * This class is responsible for actually making changes to the world. It accepts parameters to
     * define where/what it should affect (for this example the parameter is [player]), and the
     * [cast] method within is responsible for using that data to alter the world.
     */
    private data class Spell(val player: ServerPlayer) : RenderedSpell {
        override fun cast(ctx: CastingEnvironment) {
            player.sendSystemMessage(Component.translatable("text.hexmapping.congrats", player.displayName));
            HexMappingNetworking.sendToPlayer(player, SetLookPitchS2CMsg(-90f))
        }
    }
}
