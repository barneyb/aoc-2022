package com.barneyb.aoc.aoc2021.day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = "199\n" +
        "200\n" +
        "208\n" +
        "210\n" +
        "200\n" +
        "207\n" +
        "240\n" +
        "269\n" +
        "260\n" +
        "263\n"

class SonarSweepKtTest {

    @Test
    fun parse() {
        assertEquals(
            listOf<Long>(
                199,
                200,
                208,
            ), parse(
                "199\n" +
                        "200\n" +
                        "208\n"
            )
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(7, countIncreases(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(5, countIncreases(parse(EXAMPLE_ONE), 3))
    }

}
