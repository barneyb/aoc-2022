package com.barneyb.aoc.aoc2022.day24

import com.barneyb.util.Dir
import com.barneyb.util.HashSet
import com.barneyb.util.Rect
import com.barneyb.util.Vec2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
#.#####
#.....#
#>....#
#.....#
#...v.#
#.....#
#####.#
"""

private const val EXAMPLE_TWO = """
#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#
"""

class BlizzardBasinKtTest {

    @Test
    fun parsing() {
        val valley = parse(EXAMPLE_ONE)
        assertEquals(
            HashSet(
                Blizzard(Vec2(1, 2), Dir.EAST),
                Blizzard(Vec2(4, 4), Dir.SOUTH),
            ),
            valley.blizzards,
        )
        assertEquals(Rect(1, 1, 5, 5), valley.bounds)
        assertEquals(Vec2(1, 0), valley.start)
        assertEquals(Vec2(5, 6), valley.goal)
    }

    @Test
    fun exampleOne() {
        assertEquals(10, parse(EXAMPLE_ONE).stepsToGoal())
    }

    @Test
    fun exampleTwo() {
        assertEquals(18, parse(EXAMPLE_TWO).stepsToGoal())
    }

}
