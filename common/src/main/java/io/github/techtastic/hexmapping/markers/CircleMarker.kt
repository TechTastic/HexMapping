package io.github.techtastic.hexmapping.markers

import at.petrak.hexcasting.api.utils.*
import com.samsthenerd.inline.api.client.InlineClientAPI
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.phys.Vec3

data class CircleMarker(
    override val id: String,
    override val label: String,
    val center: Vec3,
    val radius: Double,
    private var lineColor: Int = IOptions.getRandomColor(),
    private var fillColor: Int = IOptions.getRandomColor(),
    private var lineWidth: Double = 1.0,
) : BaseMarker(id, label), IOptions {
    override fun toNbt(tag: CompoundTag) {
        super.toNbt(tag)

        tag.putCompound("center", center.serializeToNBT())
        tag.putDouble("radius", radius)

        tag.putInt("lineColor", lineColor)
        tag.putInt("fillColor", fillColor)
        tag.putDouble("lineWidth", lineWidth)
    }

    override fun display(): Component {
        return "circle_marker :$center, $radius".withStyle(Style.EMPTY.withColor(fillColor))
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
        fun fromNbt(tag: CompoundTag): CircleMarker {
            val circle = CircleMarker(
                tag.getString("id"),
                tag.getString("label"),
                vecFromNBT(tag.getCompound("center").downcast(CompoundTag.TYPE)),
                tag.getDouble("radius"),
                tag.getInt("lineColor"),
                tag.getInt("fillColor"),
                tag.getDouble("lineWidth")
            )

            return circle
        }
    }
}