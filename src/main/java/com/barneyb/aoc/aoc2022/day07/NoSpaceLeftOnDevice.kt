package com.barneyb.aoc.aoc2022.day07

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice
import java.util.*

fun main() {
    Solver.benchmark(
        ::parse,
        ::partOne,
        ::partTwo,
    )
}

internal fun parse(input: String): IntArray {
    val list = IntArray(200) // tee hee
    var idx = 0
    input.toSlice().trim().lines().let { lines ->
        val levels = IntArray(10) // tee hee
        var depth = -1
        for (l in lines) {
            if (l[0] == '$') { // $
                if (l[2] == 'c') { // $ cd
                    if (l[5] == '.') { // $ cd ..
                        list[idx++] = levels[depth--]
                    } else { // $ cd <dir>
                        levels[++depth] = 0
                    }
                }
            } else if (l[0] != 'd') { // <size> <file>
                val size = l.take(l.indexOf(' ')).toInt()
                for (i in 0..depth) {
                    levels[i] += size
                }
            } // dir <dir>
        }
        while (depth >= 0) {
            list[idx++] = levels[depth--]
        }
    }
    Arrays.sort(list, 0, idx)
    return list.copyOfRange(0, idx)
}

internal fun partOne(sizes: IntArray): Int {
    var sum = 0
    for (s in sizes) {
        if (s > 100_000) break
        sum += s
    }
    return sum
}

internal fun partTwo(sizes: IntArray): Int {
    val needed = 30_000_000 - (70_000_000 - sizes.last())
    var lo = 0
    var hi = sizes.size - 1
    var mid: Int
    while (lo < hi) {
        mid = (lo + hi) / 2
        if (sizes[mid] < needed) lo = mid + 1
        else if (sizes[mid] > needed) hi = mid - 1
        else return needed
    }
    return sizes[lo]
}
