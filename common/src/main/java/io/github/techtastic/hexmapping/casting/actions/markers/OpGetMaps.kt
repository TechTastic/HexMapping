package io.github.techtastic.hexmapping.casting.actions.markers

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import net.minecraft.server.level.ServerLevel
import java.util.function.Function

class OpGetMaps(private val callback: Function<ServerLevel, List<MapIota>>) : ConstMediaAction {
    override val argc: Int
        get() = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return listOf(ListIota(callback.apply(env.world)))
    }
}