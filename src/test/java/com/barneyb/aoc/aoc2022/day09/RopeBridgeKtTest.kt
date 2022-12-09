package com.barneyb.aoc.aoc2022.day09

import com.barneyb.util.Dir
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
"""

private const val EXAMPLE_TWO = """
R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
"""

class RopeBridgeKtTest {

    @Test
    fun parsing() {
        assertEquals(
            listOf(
                Dir.EAST,
                Dir.EAST,
                Dir.EAST,
                Dir.EAST,
                Dir.NORTH,
                Dir.NORTH,
                Dir.NORTH,
                Dir.NORTH,
                Dir.WEST,
                Dir.WEST,
                Dir.WEST,
                Dir.SOUTH,
                Dir.EAST,
                Dir.EAST,
                Dir.EAST,
                Dir.EAST,
                Dir.SOUTH,
                Dir.WEST,
                Dir.WEST,
                Dir.WEST,
                Dir.WEST,
                Dir.WEST,
                Dir.EAST,
                Dir.EAST,
            ), parse(EXAMPLE_ONE)
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(13, partOne(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(1, partTwo(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleThree() {
        assertEquals(36, partTwo(parse(EXAMPLE_TWO)))
    }

}
