package io.github.techtastic.hexmapping.api.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import io.github.techtastic.hexmapping.api.casting.mishaps.MishapNoIntegration
import io.github.techtastic.hexmapping.integration.IMapIntegration
import io.github.techtastic.hexmapping.registry.HexMappingIntegrationRegistry
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel

class MapIota(payload: MapDetails): Iota(TYPE, payload) {
    fun getIntegrationID() = (payload as MapDetails).integrationId

    fun getMapName() = (payload as MapDetails).mapName

    fun getMapDetails() = payload as MapDetails

    override fun isTruthy() = true

    override fun toleratesOther(that: Iota?) = false

    override fun serialize(): Tag {
        val tag = CompoundTag()
        tag.putString("integrationId", getIntegrationID())
        tag.putString("mapName", getMapName())
        return tag
    }

    companion object {
        val TYPE = object: IotaType<MapIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel?): MapIota {
                val tag = tag as CompoundTag
                return MapIota(MapDetails(tag.getString("integrationId"), tag.getString("mapName")))
            }

            override fun display(tag: Tag): Component {
                val map = deserialize(tag, null)
                return Component.literal("[item:filled_map] {${map.getIntegrationID()}, ${map.getMapName()}}").withStyle(Style.EMPTY.withColor(color()))
            }

            override fun color() = 0xDFBD81
        }
    }

    data class MapDetails(val integrationId: String, val mapName: String) {
        fun getIntegration(): IMapIntegration {
            return HexMappingIntegrationRegistry
                .getIntegration(ResourceLocation(integrationId))
                ?: throw MishapNoIntegration(integrationId)
        }
    }
}