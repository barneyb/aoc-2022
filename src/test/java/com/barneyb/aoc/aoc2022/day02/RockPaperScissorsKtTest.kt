package com.barneyb.aoc.aoc2022.day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RockPaperScissorsKtTest {

    @Test
    fun exampleOne() {
        assertEquals(
            15, partOne(
                parse(
                    "A Y\n" +
                            "B X\n" +
                            "C Z\n"
                )
            )
        )
    }
}
