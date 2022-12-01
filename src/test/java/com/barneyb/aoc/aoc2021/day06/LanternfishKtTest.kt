package com.barneyb.aoc.aoc2021.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LanternfishKtTest {

    @Test
    fun exampleOne() {
        val start = parse("3,4,3,1,2")
        assertEquals(5, nDays(start, 0))
        assertEquals(5, nDays(start, 1))
        assertEquals(6, nDays(start, 2))
        assertEquals(7, nDays(start, 3))
        assertEquals(9, nDays(start, 4))
        assertEquals(10, nDays(start, 5))
        assertEquals(10, nDays(start, 6))
        assertEquals(10, nDays(start, 7))
        assertEquals(10, nDays(start, 8))
        assertEquals(11, nDays(start, 9))
        assertEquals(12, nDays(start, 10))
        assertEquals(15, nDays(start, 11))
        assertEquals(17, nDays(start, 12))
        assertEquals(19, nDays(start, 13))
        assertEquals(20, nDays(start, 14))
        assertEquals(20, nDays(start, 15))
        assertEquals(21, nDays(start, 16))
        assertEquals(22, nDays(start, 17))
        assertEquals(26, nDays(start, 18))
        // ...
        assertEquals(5934, nDays(start, 80))
    }

    @Test
    fun exampleTwo() {
        val start = parse("3,4,3,1,2")
        assertEquals(26984457539, nDays(start, 256))
    }
}
