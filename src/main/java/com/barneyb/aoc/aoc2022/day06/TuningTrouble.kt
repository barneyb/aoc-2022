package com.barneyb.aoc.aoc2022.day06

import com.barneyb.aoc.util.Solver
import com.barneyb.util.Queue

fun main() {
    Solver.execute(
        ::parse,
        ::endOfStartPacketMarker,
        ::endOfStartMessageMarker,
    )
}

internal fun parse(input: String) =
    input.trim()

internal fun endOfStartPacketMarker(signal: String) =
    endOfStartMarker(signal, 4)

internal fun endOfStartMessageMarker(signal: String) =
    endOfStartMarker(signal, 14)

internal fun endOfStartMarker(signal: String, len: Int): Int {
    val queue = Queue<Char>()
    for ((i, c) in signal.withIndex()) {
        queue.enqueue(c)
        if (queue.size > len) queue.dequeue()
        if (queue.size == len && queue.distinct().size == len) {
            return i + 1 // one-indexing
        }
    }
    return -1
}
