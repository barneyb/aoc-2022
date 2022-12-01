package com.barneyb.aoc.aoc2020.day15

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        { getNth(it, 2020) },
        { getNth(it, 30000000) },
    )
}

internal fun parse(input: String) =
    input.trim()
        .split(",")
        .map(String::toInt)

internal fun getNth(seed: List<Int>, n: Int): Int {
    val ages = HashMap<Int, Int>()
    var curr = -1
    var priorTurn = -1
    for (turn in 0 until n) {
        if (turn < seed.size) {
            curr = seed[turn]
        } else if (priorTurn < 0) {
            curr = 0
        } else {
            curr = ages[curr]!! - priorTurn
        }
        priorTurn = ages[curr] ?: -1
        ages[curr] = turn
    }
    println("after $n turns, ${ages.size} values")
    return curr
}
