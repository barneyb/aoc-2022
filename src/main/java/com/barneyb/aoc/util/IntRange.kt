package com.barneyb.aoc.util

import kotlin.math.max
import kotlin.math.min

fun IntRange.fullyContains(other: IntRange): Boolean =
    other.first >= first && other.last <= last

fun IntRange.overlaps(other: IntRange): Boolean =
    // IntRange.intersection eagerly materializes!
    other.fullyContains(this) ||
            other.first in this ||
            other.last in this

fun IntRange.abuts(other: IntRange): Boolean =
    other.first == last + 1 ||
            other.last == first - 1

operator fun IntRange.plus(n: Int): IntRange =
    if (contains(n)) this else min(n, first)..max(n, last)

operator fun IntRange.plus(other: IntRange): IntRange {
    if (fullyContains(other)) return this
    if (overlaps(other) || abuts(other)) {
        return min(first, other.first)..max(last, other.last)
    }
    throw IllegalArgumentException("Cannot add $other to $this")
}

val IntRange.size
    get() = last - first + 1
