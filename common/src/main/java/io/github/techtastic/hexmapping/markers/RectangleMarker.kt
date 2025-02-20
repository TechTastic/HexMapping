package io.github.techtastic.hexmapping.markers

import at.petrak.hexcasting.api.utils.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.phys.Vec3

data class RectangleMarker(
    override val id: String,
    override val label: String,
    val firstCorner: Vec3,
    val secondCorner: Vec3,
    private var lineColor: Int = IOptions.getRandomColor(),
    private var fillColor: Int = IOptions.getRandomColor(),
    private var lineWidth: Double = 1.0
) : BaseMarker(id, label), IOptions {
    override fun toNbt(tag: CompoundTag) {
        super.toNbt(tag)

        tag.putCompound("firstCorner", firstCorner.serializeToNBT())
        tag.putCompound("secondCorner", secondCorner.serializeToNBT())

        tag.putInt("lineColor", lineColor)
        tag.putInt("fillColor", fillColor)
        tag.putDouble("lineWidth", lineWidth)
    }

    override fun display(): Component {
        return "rectangle_marker :$firstCorner, $secondCorner".withStyle(Style.EMPTY.withColor(fillColor))
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
        fun fromNbt(tag: CompoundTag): RectangleMarker {
            return RectangleMarker(
                tag.getString("id"),
                tag.getString("label"),
                vecFromNBT(tag.getCompound("firstCorner").downcast(CompoundTag.TYPE)),
                vecFromNBT(tag.getCompound("secondCorner").downcast(CompoundTag.TYPE)),
                tag.getInt("lineColor"),
                tag.getInt("fillColor"),
                tag.getDouble("lineWidth")
            )
        }
    }
}