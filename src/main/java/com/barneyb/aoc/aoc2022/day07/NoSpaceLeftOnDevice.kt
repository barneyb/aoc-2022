package com.barneyb.aoc.aoc2022.day07

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.benchmark(
        ::parse,
        ::partOne,
        ::partTwo,
    )
}

internal fun parse(input: String) =
    buildList {
        input.toSlice().trim().lines().let { lines ->
            val levels = IntArray(10) // tee hee
            var depth = -1
            for (l in lines) {
                if (l[0] == '$') { // $
                    if (l[2] == 'c') { // $ cd
                        if (l[5] == '.') { // $ cd ..
                            add(levels[depth--])
                        } else { // $ cd <dir>
                            levels[++depth] = 0
                        }
                    }
                } else if (l[0] != 'd') { // <size> <file>
                    val size = l.take(l.indexOf(' ')).toInt()
                    for (i in 0..depth) {
                        levels[i] += size
                    }
                }
            }
            while (depth >= 0) {
                add(levels[depth--])
            }
        }
    }

internal fun partOne(sizes: List<Int>) =
    sizes.sumOf { if (it <= 100_000) it else 0 }

internal fun partTwo(sizes: List<Int>) =
    (30_000_000 - (70_000_000 - sizes.last())).let { needed ->
        sizes.minOf { if (it >= needed) it else Int.MAX_VALUE }
    }
