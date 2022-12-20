package com.barneyb.aoc.aoc2022.day19

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::totalQualityLevel, // 1703 (24 sec)
    )
}

internal const val MINUTES_PART_ONE = 24

internal const val MINUTES_PART_TWO = 32

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(Blueprint::parse)

internal fun totalQualityLevel(bps: List<Blueprint>) =
    bps.sumOf { it.id * it.maxGeodesIn(MINUTES_PART_ONE) }

internal fun maxFromThree(bps: List<Blueprint>) =
    bps.take(3)
        .map { it.maxGeodesIn(MINUTES_PART_TWO) }
        .fold(1, Int::times)
