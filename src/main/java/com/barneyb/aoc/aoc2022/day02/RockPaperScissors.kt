package com.barneyb.aoc.aoc2022.day02

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        partOne,
        partTwo,
    )
}

internal fun parse(input: String) =
    input.trim()
        .lines()

private val tableOne: Map<String, Int> = mapOf(
    "A X" to 4,
    "B X" to 1,
    "C X" to 7,
    "A Y" to 8,
    "B Y" to 5,
    "C Y" to 2,
    "A Z" to 3,
    "B Z" to 9,
    "C Z" to 6,
)

private val tableTwo: Map<String, Int> = mapOf(
    "A X" to 3,
    "B X" to 1,
    "C X" to 2,
    "A Y" to 4,
    "B Y" to 5,
    "C Y" to 6,
    "A Z" to 8,
    "B Z" to 9,
    "C Z" to 7,
)

internal fun part(table: Map<String, Int>) =
    fun(rounds: List<String>) =
        rounds.sumOf(table::getValue)

internal val partOne = part(tableOne)

internal val partTwo = part(tableTwo)
