package com.barneyb.aoc.aoc2022.day10

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::signalStrengths,
        ::render, // RBPARAGF
    )
}

internal data class State(
    val cycle: Int = 0,
    val x: Int = 1
) {
    fun tick() =
        copy(cycle = cycle + 1)

    fun add(n: Int) =
        copy(x = x + n)

    val strength
        get() = cycle * x
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map { it ->
            if (it[0] == 'a') { // addx <N>
                it.drop(5).toInt()
            } else { // noop
                null
            }
        }

internal fun states(lines: List<Int?>) =
    sequence {
        var s = State()
        lines.forEach {
            s = s.tick()
            yield(s)
            if (it != null) { // addx <N>
                s = s.tick()
                yield(s)
                s = s.add(it)
            } // noop
        }
    }

internal fun signalStrengths(lines: List<Int?>) =
    states(lines).fold(0) { sig, s ->
        if (s.cycle % 40 == 20) sig + s.strength else sig
    }

internal fun render(lines: List<Int?>) =
    buildString {
        states(lines).forEach {
            val pixel = (it.cycle - 1) % 40
            if (pixel == 0) {
                append('\n')
            }
            val sprite = it.x - 1..it.x + 1
            val on = pixel in sprite
            append(if (on) '#' else '.')
        }
    }

