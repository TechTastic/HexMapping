package io.github.techtastic.hexmapping.fabric.platform.fabric

import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import miyucomics.hexical.casting.iota.getTrueDye
import miyucomics.hexical.entities.specklikes.MeshEntity
import net.minecraft.world.phys.Vec3

object HexMappingHexicalAbstractionsImpl {
    fun List<Iota>.getColor(idx: Int, argc: Int): Int {
        return this.getTrueDye(idx, argc).textColor
    }

    fun List<Iota>.getMesh(idx: Int, argc: Int): List<Vec3> {
        val entity = this.getEntity(idx, argc)

        if (entity !is MeshEntity)
            throw AssertionError() // TODO: Mishap

        return entity.getShape().map(Vec3Iota::getVec3)
    }
}
