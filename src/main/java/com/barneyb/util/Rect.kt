package com.barneyb.util

import kotlin.math.max
import kotlin.math.min

/** A rectangle with closed bounds. */
data class Rect(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {

    @Suppress("unused")
    constructor(min: Vec2, max: Vec2) : this(min.x, min.y, max.x, max.y)

    @Suppress("unused")
    constructor(xs: IntRange, ys: IntRange) : this(
        xs.first,
        ys.first,
        xs.last,
        ys.last
    )

    companion object {
        val EMPTY = Rect(0, 0, 0, 0)

        fun of(p: Vec2) =
            Rect(p.x, p.y, p.x, p.y)
    }

    val width get() = x2 - x1 + 1
    val height get() = y2 - y1 + 1

    val xRange get() = x1..x2
    val yRange get() = y1..y2

    fun contains(p: Vec2) =
        p.x in xRange && p.y in yRange

    fun coerceToInclude(p: Vec2) =
        if (this === EMPTY) of(p)
        else if (contains(p)) this
        else Rect(min(x1, p.x), min(y1, p.y), max(x2, p.x), max(y2, p.y))

}
