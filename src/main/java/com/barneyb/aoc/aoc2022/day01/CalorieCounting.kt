package com.barneyb.aoc.aoc2022.day01

import com.barneyb.aoc.util.Solver
import java.util.*

fun main() {
    Solver.execute(
        ::parse,
        PriorityQueue<Long>::peek,
        { pq -> pq.remove() + pq.remove() + pq.peek() }
    )
}

internal fun parse(input: String): PriorityQueue<Long> =
    PriorityQueue(Comparator.naturalOrder<Long?>().reversed()).apply {
        input.trim()
            .split("\n\n")
            .forEach() {
                add(
                    it.split("\n")
                        .map(String::toLong)
                        .reduce(Long::plus)
                )
            }
    }
