package com.barneyb.aoc.aoc2020.day15

import com.barneyb.aoc.util.Solver
import java.util.*

fun main() {
    Solver.execute(
        ::parse,
        { getNth(it, 2020) },
        { getNth(it, 30_000_000) },
    )
}

internal fun parse(input: String) =
    input.trim()
        .split(",")
        .map(String::toInt)

internal fun getNth(seed: List<Int>, n: Int): Int {
    val ages = IntArray(n.coerceAtLeast(seed.max() + 1))
    Arrays.fill(ages, -1)
    var curr = -1
    var priorTurn = -1
    for (turn in 0 until n) {
        if (turn < seed.size) {
            curr = seed[turn]
        } else if (priorTurn < 0) {
            curr = 0
        } else {
            curr = ages[curr] - priorTurn
        }
        priorTurn = ages[curr]
        ages[curr] = turn
    }
    return curr
}
