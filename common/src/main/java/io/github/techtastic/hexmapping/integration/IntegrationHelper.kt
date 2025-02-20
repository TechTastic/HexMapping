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
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.*
import java.net.URL
import java.util.*
import javax.imageio.ImageIO

object IntegrationHelper {
    const val ICON_WIDTH: Int = 24
    const val ICON_HEIGHT: Int = 24

    fun sanitizeHtml(html: String?): String {
        return StringEscapeUtils.escapeHtml4(html)
    }

    fun downloadAndResizeIconBufferedImage(url: URL?): BufferedImage? {
        try {
            val icon = ImageIO.read(url) ?: return null
            val resizedIcon = BufferedImage(ICON_WIDTH, ICON_HEIGHT, BufferedImage.TYPE_INT_ARGB)
            val g2d = resizedIcon.createGraphics()
            g2d.addRenderingHints(RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY))
            g2d.drawImage(icon, 0, 0, ICON_WIDTH, ICON_HEIGHT, null)
            return resizedIcon
        } catch (e: Exception) {
            //ComputerCartographer.logException(e);
        }
        return null
    }

    fun downloadAndResizeIcon(url: URL?): InputStream? {
        val resizedIcon = downloadAndResizeIconBufferedImage(url)
        if (resizedIcon != null) {
            try {
                val outStream = ByteArrayOutputStream()
                ImageIO.write(resizedIcon, "png", outStream)
                return ByteArrayInputStream(outStream.toByteArray())
            } catch (e: IOException) {
                //ComputerCartographer.logException(e);
            }
        }
        return null
    }

    fun downloadAndResizeIcon(url: URL?, output: OutputStream): Boolean {
        try {
            val resizedIcon = downloadAndResizeIconBufferedImage(url)
            if (resizedIcon != null) {
                ImageIO.write(resizedIcon, "png", output)
                output.flush()
                output.close()
                return true
            }
        } catch (e: Exception) {
            //ComputerCartographer.logException(e);
        }
        return false
    }

    fun getDirectoriesWithRegionDirectory(dir: File): ArrayList<String> {
        val matches = ArrayList<String>()
        return getDirectoriesWithRegionDirectory(dir, matches)
    }

    fun getDirectoriesWithRegionDirectory(dir: File, matches: ArrayList<String>): ArrayList<String> {
        val ls = dir.listFiles()
        for (file in Objects.requireNonNull(ls)) {
            if (file.isDirectory && file.name == "region") {
                matches.add(file.parentFile.name)
            } else if (file.isDirectory) {
                getDirectoriesWithRegionDirectory(file, matches)
            }
        }
        return matches
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
            return this.getPositiveIntUnderInclusive(idx, 0xFFFFFFFF.toInt(), argc)
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
