package io.github.techtastic.hexmapping.markers

import kotlin.random.Random

interface IOptions {
    fun hasFillColor(): Boolean
    fun hasLineColor(): Boolean
    fun hasLineWeight(): Boolean

    fun getFillColor(): Int?
    fun getLineColor(): Int?
    fun getLineWeight(): Double?

    fun setFillColor(color: Int)
    fun setLineColor(color: Int)
    fun setLineWeight(weight: Double)

    companion object {
        fun getRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
    }
}