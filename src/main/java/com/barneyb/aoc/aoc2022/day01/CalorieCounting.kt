package com.barneyb.aoc.aoc2022.day01

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toLong
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.MinPQ

fun main() {
    Solver.benchmark(
        ::parse,
        ::topOne, // 70,296
        ::topThree, // 205,381
    )
}

internal fun parse(input: String) =
    buildList {
        input.toSlice()
            .trim()
            .split("\n\n")
            .forEach() {
                add(
                    it.split("\n")
                        .map(Slice::toLong)
                        .reduce(Long::plus)
                )
            }
    }

internal fun topOne(elves: List<Long>) =
    elves.max()

internal fun topThree(elves: List<Long>) =
    MinPQ<Long>().apply {
        for (e in elves) {
            add(e)
            if (size > 3) remove()
        }
    }.sum()
