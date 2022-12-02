package com.barneyb.aoc.aoc2022.day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE = "A Y\n" +
        "B X\n" +
        "C Z\n"

class RockPaperScissorsKtTest {

    @Test
    fun exampleOne() {
        assertEquals(15, partOne(parse(EXAMPLE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(12, partTwo(parse(EXAMPLE)))
    }

}
