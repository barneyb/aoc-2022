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
        assertEquals(13, positionsVisited(parse(EXAMPLE_ONE)))
    }

}
