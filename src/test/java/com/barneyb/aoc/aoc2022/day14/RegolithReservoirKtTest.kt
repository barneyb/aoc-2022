package com.barneyb.aoc.aoc2022.day14

import com.barneyb.aoc.util.Slice
import com.barneyb.util.HashSet
import com.barneyb.util.Vec2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9
"""

class RegolithReservoirKtTest {

    @Test
    fun parseAndDraw() {
        val actual = Map(parse(EXAMPLE_ONE)).toString()
        println("=".repeat(80))
        println(actual)
        println("=".repeat(80))
        assertEquals(
            """

              4      5   5
              9      0   0
              3      0   4
            0 .......+....
            1 ............
            2 ............
            3 ............
            4 .....#...##.
            5 .....#...#..
            6 ...###...#..
            7 .........#..
            8 .........#..
            9 .#########..
            """.trimIndent(), actual
        )
    }

    @Test
    fun formations() {
        assertEquals(
            HashSet(
                Vec2(498, 4),
                Vec2(498, 5),
                Vec2(498, 6),
                Vec2(497, 6),
                Vec2(496, 6),
            ), parseFormation(Slice("498,4 -> 498,6 -> 496,6"))
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(24, sandAtRestAbyss(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(93, sandAtRestFloor(parse(EXAMPLE_ONE)))
    }
}
