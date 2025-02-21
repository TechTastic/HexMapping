package io.github.techtastic.hexmapping.integration

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getPositiveIntUnderInclusive
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import dev.architectury.platform.Platform
import io.github.techtastic.hexmapping.api.casting.iota.MapIota
import io.github.techtastic.hexmapping.api.casting.iota.MarkerIota
import io.github.techtastic.hexmapping.markers.BaseMarker
import io.github.techtastic.hexmapping.platform.HexMappingHexicalAbstractions.getMesh
import net.minecraft.world.phys.Vec3
import org.apache.commons.lang3.StringEscapeUtils

object IntegrationHelper {
    const val ICON_WIDTH: Int = 24
    const val ICON_HEIGHT: Int = 24

    fun sanitizeHtml(html: String?): String {
        return StringEscapeUtils.escapeHtml4(html)
    }

    fun List<Iota>.getMarker(idx: Int, argc: Int): BaseMarker {
        val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
        if (x is MarkerIota) {
            return x.getMarker()
        } else {
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "marker")
        }
    }

    fun List<Iota>.getMap(idx: Int, argc: Int): MapIota.MapDetails {
        val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
        if (x is MapIota) {
            return x.getMapDetails()
        } else {
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "map")
        }
    }

    fun List<Iota>.getColor(idx: Int, argc: Int): Int {
        try {
            return this.getPositiveIntUnderInclusive(idx, 0xFFFFFF, argc)
        } catch (m: MishapInvalidIota) {
            if (Platform.isModLoaded("hexical"))
                return this.getColor(idx, argc)
            throw m
        }
    }

    fun List<Iota>.getPoints(idx: Int, argc: Int): List<Vec3> {
        try {
            return this.getList(2, argc).filterIsInstance<Vec3Iota>().map(Vec3Iota::getVec3)
        } catch (m: MishapInvalidIota) {
            if (Platform.isModLoaded("hexical"))
                return this.getMesh(idx, argc)
            throw m
        }
    }

    fun CastingEnvironment.getMarkerSetOfCaster() =
        this.castingEntity?.stringUUID ?: "unknown"
}
