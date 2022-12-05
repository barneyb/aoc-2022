package com.barneyb.aoc.aoc2021.day01

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        ::countIncreases
    ) { countIncreases(it, 3) }
}

internal fun parse(input: String) =
    input.trim().lines().map(String::toLong)

internal fun countIncreases(readings: List<Long>, step: Int = 1) =
    readings.windowed(step + 1)
        .count { it.first() < it.last() }
