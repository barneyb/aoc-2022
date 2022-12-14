package com.barneyb.aoc.aoc2022.day08

import com.barneyb.util.Vec2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

private const val EXAMPLE_ONE = """
30373
25512
65332
33549
35390
"""

class TreetopTreeHouseKtTest {

    @Test
    fun parsing() {
        assertEquals(
            listOf(
                listOf(3, 0, 3, 7, 3),
                listOf(2, 5, 5, 1, 2),
                listOf(6, 5, 3, 3, 2),
                listOf(3, 3, 5, 4, 9),
                listOf(3, 5, 3, 9, 0),
            ), parse(EXAMPLE_ONE)
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(21, visibleTreeCount(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertAll(
            { assertEquals(4, scenicScore(parse(EXAMPLE_ONE), Vec2(2, 1))) },
            { assertEquals(8, scenicScore(parse(EXAMPLE_ONE), Vec2(2, 3))) },
            { assertEquals(1, scenicScore(parse(EXAMPLE_ONE), Vec2(1, 3))) },
        )
    }

    @Test
    fun exampleTwoB() {
        assertEquals(8, bestScenicScore(parse(EXAMPLE_ONE)))
    }

}
