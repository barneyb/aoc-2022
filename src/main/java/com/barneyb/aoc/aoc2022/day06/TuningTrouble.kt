package com.barneyb.aoc.aoc2022.day06

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.benchmark(
        ::parse,
        ::endOfStartPacketMarker,
        ::endOfStartMessageMarker,
    )
}

internal fun parse(input: String) =
    input.toSlice().trim()

internal fun endOfStartPacketMarker(signal: CharSequence) =
    endOfStartMarker(signal, 4)

internal fun endOfStartMessageMarker(signal: CharSequence) =
    endOfStartMarker(signal, 14)

private fun endOfStartMarker(signal: CharSequence, len: Int): Int {
    val hist = IntArray(26)
    var dupes = 0
    for ((idx, c) in signal.withIndex()) {
        if (++hist[c - 'a'] == 2) dupes++
        if (idx >= len) {
            if (--hist[signal[idx - len] - 'a'] == 1) dupes--
            if (dupes == 0) {
                return idx + 1 // one-indexing
            }
        }
    }
    throw IllegalArgumentException("No marker found in signal")
}
