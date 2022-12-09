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
        ::partOne,
        ::partTwo, // 2140 is too low
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

internal fun partOne(steps: List<Dir>) =
    positionsVisited(steps, 2)

internal fun partTwo(steps: List<Dir>) =
    positionsVisited(steps, 10)

internal fun positionsVisited(steps: List<Dir>, knots: Int): Int {
    val rope = Array<Vec2>(knots) { Vec2.origin() }
    val visited = HashSet<Vec2>()
    visited.add(rope.last())
    for (step in steps) {
        rope[0] = rope[0].move(step)
        for (i in 1 until rope.size) {
            val dx = rope[i - 1].x - rope[i].x
            val dy = rope[i - 1].y - rope[i].y
            if (abs(dx) == 2) {
                if (abs(dy) == 2) {
                    rope[i] += Vec2(dx / 2, dy / 2)
                } else {
                    rope[i] += Vec2(dx / 2, dy)
                }
            } else if (abs(dy) == 2) {
                rope[i] += Vec2(dx, dy / 2)
            }
        }
        visited.add(rope.last())
//        println()
//        for (y in -4..0) {
//            for (x in 0..5) {
//                val v = Vec2(x, y)
//                var found = false
//                for ((i, k) in rope.withIndex()) {
//                    if (k != v) continue
//                    found = true
//                    if (i == 0) print("H")
//                    else print(i)
//                    break
//                }
//                if (!found) print(".")
//            }
//            println()
//        }
    }
    return visited.size
}
