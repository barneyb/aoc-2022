package com.barneyb.aoc.aoc2022.day02

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        ::partOne,
    )
}

internal fun parse(input: String) =
    input.trim()
        .lines()
        .map { Pair(it.first(), it.last()) }

internal fun partOne(rounds: List<Pair<Char, Char>>) =
    rounds.map { (e, m) ->
        when (m) {
            'X' -> 1 + when (e) {
                'A' -> 3
                'B' -> 0
                'C' -> 6
                else -> throw IllegalArgumentException("No '$e' play?!")
            }

            'Y' -> 2 + when (e) {
                'A' -> 6
                'B' -> 3
                'C' -> 0
                else -> throw IllegalArgumentException("No '$e' play?!")
            }

            'Z' -> 3 + when (e) {
                'A' -> 0
                'B' -> 6
                'C' -> 3
                else -> throw IllegalArgumentException("No '$e' play?!")
            }

            else -> throw IllegalArgumentException("No '$m' play?!")
        }
    }.sum()
