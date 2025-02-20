package io.github.techtastic.hexmapping.api.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import net.minecraft.network.chat.Component

class MishapBadMap(val modId: String, val mapName: String): Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context) =
        ctx.pigment

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) =
        Component.translatable("hexmapping.mishap.bad_map", mapName, modId)

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {}
}