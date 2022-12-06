package com.barneyb.aoc.aoc2022.day06

import com.barneyb.aoc.util.Solver
import com.barneyb.util.Queue

fun main() {
    Solver.benchmark(
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

private fun endOfStartMarker(signal: String, len: Int): Int {
    val hist = IntArray(26)
    var dupes = 0
    val queue = Queue<Char>()
    for ((idx, c) in signal.withIndex()) {
        queue.enqueue(c)
        if (++hist[c - 'a'] == 2) dupes++
        if (queue.size > len) {
            if (--hist[queue.dequeue() - 'a'] == 1) dupes--
        }
        if (queue.size == len && dupes == 0) {
            return idx + 1 // one-indexing
        }
    }
    return -1
}
