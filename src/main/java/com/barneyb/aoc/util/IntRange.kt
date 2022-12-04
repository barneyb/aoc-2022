package com.barneyb.aoc.util

fun IntRange.fullyContains(other: IntRange): Boolean =
    other.first >= first && other.last <= last

fun IntRange.overlaps(other: IntRange): Boolean =
    // IntRange.intersection eagerly materializes!
    other.fullyContains(this) ||
            other.first in this ||
            other.last in this
