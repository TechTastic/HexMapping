package io.github.techtastic.hexmapping.markers

import at.petrak.hexcasting.api.utils.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.phys.Vec3

data class PolygonMarker(
    override val id: String,
    override val label: String,
    val points: List<Vec3>,
    private var lineColor: Int = IOptions.getRandomColor(),
    private var fillColor: Int = IOptions.getRandomColor(),
    private var lineWidth: Double = 1.0,
) : BaseMarker(id, label), IOptions {
    override fun toNbt(tag: CompoundTag) {
        super.toNbt(tag)

        val list = ListTag()
        points.forEach { vec -> list.add(vec.serializeToNBT()) }
        tag.putList("points", list)

        tag.putInt("lineColor", lineColor)
        tag.putInt("fillColor", fillColor)
        tag.putDouble("lineWidth", lineWidth)
    }

    override fun display(): Component {
        return "polygon_marker :${points.size} Points".withStyle(Style.EMPTY.withColor(fillColor))
    }

    override fun hasFillColor() = true

    override fun hasLineColor() = true

    override fun hasLineWeight() = true

    override fun getFillColor() = fillColor

    override fun getLineColor() = lineColor

    override fun getLineWeight() = lineWidth

    override fun setFillColor(color: Int) {
        fillColor = color
    }

    override fun setLineColor(color: Int) {
        lineColor = color
    }

    override fun setLineWeight(weight: Double) {
        lineWidth = weight
    }

    companion object {
        fun fromNbt(tag: CompoundTag): PolygonMarker {
            return PolygonMarker(
                tag.getString("id"),
                tag.getString("label"),
                tag.getList("points", Tag.TAG_COMPOUND).map { vecFromNBT(it.downcast(CompoundTag.TYPE)) },
                tag.getInt("lineColor"),
                tag.getInt("fillColor"),
                tag.getDouble("lineWidth")
            )
        }
    }
}