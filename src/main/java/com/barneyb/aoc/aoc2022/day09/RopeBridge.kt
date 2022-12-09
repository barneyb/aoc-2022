package com.barneyb.aoc.aoc2022.day09

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.Vec2
import kotlin.math.abs

fun main() {
    Solver.execute(
        ::parse,
        ::positionsVisited
    )
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .flatMap {
            val d = Dir.parse(it[0])
            buildList {
                for (i in 1..it.drop(2).toInt()) {
                    add(d)
                }
            }
        }

internal fun positionsVisited(steps: List<Dir>): Int {
    var head = Vec2.origin()
    var tail = Vec2.origin()
    val visited = HashSet<Vec2>()
    visited.add(tail)
    for (step in steps) {
        head = head.move(step)
        val dx = head.x - tail.x
        val dy = head.y - tail.y
        if (abs(dy) == 2) {
            tail += Vec2(dx, dy / 2)
        } else if (abs(dx) == 2) {
            tail += Vec2(dx / 2, dy)
        }
        visited.add(tail)
    }
    return visited.size
}
