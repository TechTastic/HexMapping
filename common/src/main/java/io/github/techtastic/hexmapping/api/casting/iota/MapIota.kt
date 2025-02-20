package io.github.techtastic.hexmapping.api.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapNoIntegration
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.integration.bluemap.BlueMapIntegration
import io.github.techtastic.hexmapping.integration.dynmap.DynMapIntegration
import io.github.techtastic.hexmapping.integration.pl3xmap.Pl3xMapIntegration
import io.github.techtastic.hexmapping.integration.squaremap.SquareMapIntegration
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.server.level.ServerLevel
import org.spongepowered.include.com.google.gson.internal.bind.SqlDateTypeAdapter

class MapIota(payload: MapDetails): Iota(TYPE, payload) {
    fun getModID() = (payload as MapDetails).modId

    fun getMapName() = (payload as MapDetails).mapName

    fun getMapDetails() = payload as MapDetails

    override fun isTruthy() = true

    override fun toleratesOther(that: Iota?) = false

    override fun serialize(): Tag {
        val tag = CompoundTag()
        tag.putString("modId", getModID())
        tag.putString("mapName", getMapName())
        return tag
    }

    companion object {
        val TYPE = object: IotaType<MapIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel?): MapIota {
                val tag = tag as CompoundTag
                return MapIota(MapDetails(tag.getString("modId"), tag.getString("mapName")))
            }

            override fun display(tag: Tag): Component {
                val map = deserialize(tag, null)
                return Component.literal("[item:filled_map] {${map.getModID()}, ${map.getMapName()}}").withStyle(Style.EMPTY.withColor(color()))
            }

            override fun color() = 0xDFBD81
        }
    }

    data class MapDetails(val modId: String, val mapName: String) {
        fun getIntegration(): IMapIntegration {
            return when (modId) {
                "bluemap" -> BlueMapIntegration
                "dynmap" -> DynMapIntegration
                "pl3xmap" -> Pl3xMapIntegration
                "squaremap" -> SquareMapIntegration
                else -> throw MishapNoIntegration(modId)
            }
        }
    }
}