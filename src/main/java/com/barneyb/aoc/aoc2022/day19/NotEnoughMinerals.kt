package com.barneyb.aoc.aoc2022.day19

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::totalQualityLevel, // 1703 (24 sec)
    )
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(Blueprint::parse)

internal fun totalQualityLevel(bps: List<Blueprint>) =
    bps.sumOf(Blueprint::qualityLevel)
