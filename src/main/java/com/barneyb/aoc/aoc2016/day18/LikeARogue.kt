package com.barneyb.aoc.aoc2016.day18

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        { safeTileCount(it, 40) },
        { safeTileCount(it, 400000) },
    )
}

internal fun parse(input: String) =
    input.trim().let {
        val arr = BooleanArray(it.length)
        for (i in it.indices) {
            if (input[i] == '^') arr[i] = true
        }
        arr
    }

internal fun safeTileCount(start: String, rows: Int) =
    safeTileCount(parse(start), rows)

internal fun safeTileCount(start: BooleanArray, rows: Int): Int {
    var count = 0
    var curr = start
    var next: BooleanArray
    var left: Boolean
    var center: Boolean
    var right: Boolean
    for (r in 1..rows) {
        next = BooleanArray(curr.size)
        for (i in curr.indices) {
            center = curr[i]
            if (!center) count++
            left = i > 0 && curr[i - 1]
            right = i < curr.size - 1 && curr[i + 1]
            if (left && center && !right)
                next[i] = true
            else if (!left && center && right)
                next[i] = true
            else if (left && !center && !right)
                next[i] = true
            else if (!left && !center && right)
                next[i] = true
        }
        curr = next
    }
    return count
}
