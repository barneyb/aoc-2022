package com.barneyb.aoc.aoc2022.day25

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::totalFuel, // 2-212-2---=00-1--102
    )
}

internal fun CharSequence.fromSnafu() =
    this.fold(0L) { n, c ->
        n * 5 + when (c) {
            '2' -> 2
            '1' -> 1
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> throw IllegalArgumentException("unrecognized '$c' digit")
        }
    }

internal fun Long.toSnafu() =
    buildString {
        var n = this@toSnafu
        while (n > 0) {
            when (val d = n % 5) {
                0L, 1L, 2L -> append(d)
                3L -> {
                    n += 5
                    append('=')
                }
                4L -> {
                    n += 5
                    append('-')
                }
            }
            n /= 5
        }
        reverse()
    }

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()


internal fun totalFuel(numbers: List<CharSequence>) =
    numbers.sumOf(CharSequence::fromSnafu)
        .toSnafu()

