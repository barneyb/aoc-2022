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
}
