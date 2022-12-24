package com.barneyb.aoc.aoc2022.day24

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.HashSet
import com.barneyb.util.Rect
import com.barneyb.util.Vec2

fun main() {
    Solver.execute(
        ::parse,
        Valley::stepsToGoal, // 299
    )
}

internal const val OPEN = '.'
internal const val WALL = '#'
internal const val NORTH = '^'
internal const val SOUTH = 'v'
internal const val WEST = '<'
internal const val EAST = '>'

internal data class Blizzard(
    val pos: Vec2,
    val heading: Dir,
)

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .let { lines ->
            Valley(
                start = Vec2(lines.first().indexOf(OPEN), 0),
                goal = Vec2(lines.last().indexOf(OPEN), lines.size - 1),
                bounds = Rect(
                    1..lines.first().length - 2,
                    1..lines.size - 2,
                ),
                blizzards = HashSet<Blizzard>().apply {
                    for ((y, l) in lines.withIndex()) {
                        for ((x, c) in l.withIndex()) {
                            when (c) {
                                OPEN -> {}
                                WALL -> {}
                                else -> add(Blizzard(Vec2(x, y), Dir.parse(c)))
                            }
                        }
                    }
                },
            )
        }
