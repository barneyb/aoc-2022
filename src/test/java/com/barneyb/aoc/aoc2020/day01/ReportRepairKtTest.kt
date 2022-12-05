package com.barneyb.aoc.aoc2020.day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = "1721\n" +
        "979\n" +
        "366\n" +
        "299\n" +
        "675\n" +
        "1456\n"

class ReportRepairKtTest {

    @Test
    fun parse() {
        assertEquals(
            listOf<Long>(
                299,
                366,
                675,
                979,
                1456,
                1721,
            ), parse(EXAMPLE_ONE)
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(514579, findIt(parse(EXAMPLE_ONE)))
    }

}
