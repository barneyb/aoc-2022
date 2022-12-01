package com.barneyb.aoc.aoc2020.day15

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RambunctiousRecitationKtTest {

    @Test
    fun parse() {
        assertEquals(listOf(1, 2, 3), parse("1,2,3\n"))
    }

    @Test
    fun exampleOne() {
        val seed = listOf(0, 3, 6)
        assertEquals(0, getNth(seed, 1))
        assertEquals(3, getNth(seed, 2))
        assertEquals(6, getNth(seed, 3))
        assertEquals(0, getNth(seed, 4))
        assertEquals(3, getNth(seed, 5))
        assertEquals(3, getNth(seed, 6))
        assertEquals(1, getNth(seed, 7))
        assertEquals(0, getNth(seed, 8))
        assertEquals(4, getNth(seed, 9))
        assertEquals(0, getNth(seed, 10))
    }

    @Test
    fun moreExamples() {
        assertAll(
            { assertEquals(1, getNth(listOf(1, 3, 2), 2020)) },
            { assertEquals(10, getNth(listOf(2, 1, 3), 2020)) },
            { assertEquals(27, getNth(listOf(1, 2, 3), 2020)) },
            { assertEquals(78, getNth(listOf(2, 3, 1), 2020)) },
            { assertEquals(438, getNth(listOf(3, 2, 1), 2020)) },
            { assertEquals(1836, getNth(listOf(3, 1, 2), 2020)) },
        )
    }

}
