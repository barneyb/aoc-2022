package com.barneyb.aoc.aoc2022.day10

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::execute,
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

internal fun states(lines: List<CharSequence>) =
    sequence<State> {
        var s = State()
        lines.forEach { l ->
            s = s.tick()
            yield(s)
            if (l[0] == 'a') { // addx <N>
                s = s.tick()
                yield(s)
                s = s.add(l.drop(5).toInt())
            } // noop
        }
    }

internal fun execute(lines: List<CharSequence>) =
    states(lines).fold(0) { sig, s ->
        if (s.cycle % 40 == 20) sig + s.strength else sig
    }

