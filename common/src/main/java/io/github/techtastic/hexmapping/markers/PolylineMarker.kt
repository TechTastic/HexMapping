package io.github.techtastic.hexmapping.markers

import at.petrak.hexcasting.api.utils.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.phys.Vec3
import kotlin.random.Random

data class PolylineMarker(
    override val id: String,
    override val label: String,
    val points: List<Vec3>,
    private var color: Int = IOptions.getRandomColor(),
    private var width: Double = 1.0,
) : BaseMarker(id, label), IOptions {
    override fun toNbt(tag: CompoundTag) {
        super.toNbt(tag)

        val list = ListTag()
        points.forEach { vec -> list.add(vec.serializeToNBT()) }
        tag.putList("points", list)

        tag.putInt("color", color)
        tag.putDouble("width", width)
    }

    override fun display(): Component {
        return "polyline_marker :${points.size} Points".withStyle(Style.EMPTY.withColor(color))
    }

    override fun hasFillColor() = false

    override fun hasLineColor() = true

    override fun hasLineWeight() = true

    override fun getFillColor() = null

    override fun getLineColor() = color

    override fun getLineWeight() = width

    override fun setFillColor(color: Int) {}

    override fun setLineColor(color: Int) {
        this.color = color
    }

    override fun setLineWeight(weight: Double) {
        width = weight
    }

    companion object {
        fun fromNbt(tag: CompoundTag): PolylineMarker {
            return PolylineMarker(
                tag.getString("id"),
                tag.getString("label"),
                tag.getList("points", Tag.TAG_COMPOUND).map { vecFromNBT(it.downcast(CompoundTag.TYPE)) },
                tag.getInt("color"),
                tag.getDouble("width")
            )
        }
    }
}