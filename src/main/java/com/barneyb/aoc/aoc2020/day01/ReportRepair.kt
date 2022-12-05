package com.barneyb.aoc.aoc2020.day01

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toLong

fun main() {
    Solver.execute(
        ::parse,
        ::findIt
    )
}

internal fun parse(input: String) =
    input.trim()
        .lines()
        .map(CharSequence::toLong)
        .sorted()

internal fun findIt(sortedExpenses: List<Long>) =
    sortedExpenses.find { n ->
        sortedExpenses.binarySearch(2020 - n) > 0
    }.let { n ->
        n!! * (2020 - n)
    }
