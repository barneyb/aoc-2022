package com.barneyb.aoc.aoc2022.day06

import com.barneyb.aoc.util.Solver
import com.barneyb.util.Queue

fun main() {
    Solver.execute(
        ::parse,
        ::endOfStartPacketMarker
    )
}

internal fun parse(input: String) =
    input.trim()

internal fun endOfStartPacketMarker(signal: String): Int {
    val queue = Queue<Char>()
    for ((i, c) in signal.withIndex()) {
        queue.enqueue(c)
        if (queue.size > 4) queue.dequeue()
        if (queue.size == 4 && queue.distinct().size == 4) {
            return i + 1 // one-indexing
        }
    }
    return -1
}
