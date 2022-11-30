package com.barneyb.aoc.aoc2016.day18

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        String::trim,
        { safeTileCount(it, 40) },
        String::length
    )
}

internal fun safeTileCount(start: CharSequence, rows: Int): Int {
    var count = 0
    var curr = start
    var next: StringBuilder
    var left: Char
    var center: Char
    var right: Char
    for (r in 1..rows) {
        next = StringBuilder(curr.length)
        for (i in curr.indices) {
            center = curr[i]
            if (center == '.') count++
            left = if (i == 0) '.' else curr[i - 1]
            right = if (i == curr.length - 1) '.' else curr[i + 1]
            if (left == '^' && center == '^' && right != '^')
                next.append('^')
            else if (left != '^' && center == '^' && right == '^')
                next.append('^')
            else if (left == '^' && center != '^' && right != '^')
                next.append('^')
            else if (left != '^' && center != '^' && right == '^')
                next.append('^')
            else
                next.append('.')
        }
        curr = next
    }
    return count
}
