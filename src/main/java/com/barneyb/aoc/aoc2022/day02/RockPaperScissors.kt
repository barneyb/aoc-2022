package com.barneyb.aoc.aoc2022.day02

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        ::partOne,
        ::partTwo,
    )
}

internal fun parse(input: String) =
    input.trim()
        .lines()

internal fun partOne(rounds: List<String>) =
    rounds.map {
        when (it) {
            "A X" -> 4
            "B X" -> 1
            "C X" -> 7
            "A Y" -> 8
            "B Y" -> 5
            "C Y" -> 2
            "A Z" -> 3
            "B Z" -> 9
            "C Z" -> 6
            else -> throw IllegalArgumentException("No '$it' play?!")
        }
    }.sum()

internal fun partTwo(rounds: List<String>) =
    rounds.map {
        when (it) {
            "A X" -> 3
            "B X" -> 1
            "C X" -> 2
            "A Y" -> 4
            "B Y" -> 5
            "C Y" -> 6
            "A Z" -> 8
            "B Z" -> 9
            "C Z" -> 7
            else -> throw IllegalArgumentException("No '$it' play?!")
        }
    }.sum()
