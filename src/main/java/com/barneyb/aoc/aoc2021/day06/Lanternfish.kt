package com.barneyb.aoc.aoc2021.day06

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        { nDays(it, 80) },
        { nDays(it, 256) },
    )
}

fun parse(input: String): LongArray {
    val hist = LongArray(9)
    input.trim()
        .split(',')
        .map(String::toInt)
        .forEach {
            hist[it] = hist[it] + 1 // += is a compile error?!
        }
    return hist
}

fun nDays(hist: LongArray, n: Int): Long {
    var curr = hist
    for (i in 1..n) {
        val next = curr.clone()
        for (j in next.indices) {
            when (j) {
                6 -> next[j] = curr[j + 1] + curr[0]
                8 -> next[j] = curr[0]
                else -> next[j] = curr[j + 1]
            }
        }
        curr = next
    }
    return curr.sum()
}
