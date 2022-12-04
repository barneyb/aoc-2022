package com.barneyb.aoc.aoc2022.day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = "2-4,6-8\n" +
        "2-3,4-5\n" +
        "5-7,7-9\n" +
        "2-8,3-7\n" +
        "6-6,4-6\n" +
        "2-6,4-8\n"

class CampCleanupKtTest {

    @Test
    fun parse() {
        assertEquals(
            listOf(
                Pair(2..4, 6..8),
                Pair(2..8, 3..7),
            ), parse(
                "2-4,6-8\n" +
                        "2-8,3-7\n"
            )
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(2, countContained(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(4, countOverlapping(parse(EXAMPLE_ONE)))
    }

    @Test
    fun overlaps() {
        assertEquals(
            6, countOverlapping(
                parse(
                    "1-2,4-5\n" + // before
                            "3-5,1-3\n" + // overlap start
                            "1-5,2-3\n" + // contained
                            "1-3,2-4\n" + // overlap end
                            "1-2,4-5\n" + // after
                            // reverse order
                            "4-5,1-2\n" +
                            "1-3,3-5\n" +
                            "2-3,1-5\n" +
                            "2-4,1-3\n" +
                            "4-5,1-2\n"
                )
            )
        )
    }
}
