package com.barneyb.aoc.aoc2022.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi
"""

class HillClimbingAlgorithmKtTest {

    @Test
    fun exampleOne() {
        assertEquals(31, parse(EXAMPLE_ONE))
    }

}
