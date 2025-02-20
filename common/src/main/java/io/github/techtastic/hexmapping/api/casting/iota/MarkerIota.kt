package io.github.techtastic.hexmapping.api.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import io.github.techtastic.hexmapping.markers.*
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import java.util.function.Function

class MarkerIota(val markerType: Type, payload: BaseMarker) : Iota(TYPE, payload) {
    fun getMarker() = payload as BaseMarker

    override fun isTruthy() = true

    override fun toleratesOther(that: Iota) = false

    override fun serialize(): Tag {
        val tag = CompoundTag()

        tag.putString("type", markerType.toString())
        getMarker().toNbt(tag)

        return tag
    }

    companion object {
        val TYPE = object: IotaType<MarkerIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel?): MarkerIota {
                val tag = tag as CompoundTag
                val type = Type.valueOf(tag.getString("type"))

                return MarkerIota(type, type.deserializer.apply(tag))
            }

            override fun display(tag: Tag): Component {
                val iota = deserialize(tag, null)
                return iota.getMarker().display()
            }

            override fun color() = ChatFormatting.AQUA.color ?: 0xFFFFFF
        }

        fun getType(marker: BaseMarker) = when (marker) {
            is CircleMarker -> Type.CIRCLE
            is RectangleMarker -> Type.RECTANGLE
            is PolygonMarker -> Type.POLYGON
            is PolylineMarker -> Type.POLYLINE
            else -> Type.ICON
        }
    }

    enum class Type(val deserializer: Function<CompoundTag, BaseMarker>) {
        ICON(IconMarker::fromNbt),
        POLYLINE(PolylineMarker::fromNbt),
        RECTANGLE(RectangleMarker::fromNbt),
        CIRCLE(CircleMarker::fromNbt),
        POLYGON(PolygonMarker::fromNbt)
    }
}