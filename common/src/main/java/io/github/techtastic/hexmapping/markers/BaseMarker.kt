package io.github.techtastic.hexmapping.markers

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component

abstract class BaseMarker(
    open val id: String,
    open val label: String
) {
    open fun toNbt(tag: CompoundTag) {
        tag.putString("id", id)
        tag.putString("label", label)
    }

    abstract fun display(): Component
}