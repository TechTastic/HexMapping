package io.github.techtastic.hexmapping.markers

import at.petrak.hexcasting.api.utils.*
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.phys.Vec3

data class IconMarker(
    override val id: String,
    override val label: String,
    val icon: String,
    val pos: Vec3
) : BaseMarker(id, label) {
    override fun toNbt(tag: CompoundTag) {
        super.toNbt(tag)

        tag.putString("icon", icon)
        tag.putCompound("pos", pos.serializeToNBT())
    }

    override fun display(): Component {
        return "icon_marker :$pos, $icon".withStyle(ChatFormatting.AQUA)
    }

    companion object {
        fun fromNbt(tag: CompoundTag): IconMarker {
            return IconMarker(
                tag.getString("id"),
                tag.getString("label"),
                tag.getString("icon"),
                vecFromNBT(tag.getCompound("pos").downcast(CompoundTag.TYPE))
            )
        }
    }
}