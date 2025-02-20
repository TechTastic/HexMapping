package io.github.techtastic.hexmapping.platform

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.world.phys.Vec3
import kotlin.jvm.Throws

object HexMappingHexicalAbstractions {
    @ExpectPlatform
    @JvmStatic
    @Throws(Mishap::class)
    fun List<Iota>.getColor(idx: Int, argc: Int): Int? {
        throw AssertionError()
    }

    @ExpectPlatform
    @JvmStatic
    @Throws(Mishap::class)
    fun List<Iota>.getMesh(idx: Int, argc: Int): List<Vec3> {
        throw AssertionError()
    }
}