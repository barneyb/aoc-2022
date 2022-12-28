package com.barneyb.aoc.aoc2022.day01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val EXAMPLE_ONE = """
1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
"""

class CalorieCountingKtTest {

    @Test
    fun parsing() {
        assertEquals(
            listOf<Long>(
                6000,
                4000,
                11000,
                24000,
                10000,
            ),
            parse(EXAMPLE_ONE)
        )
    }

    @Test
    fun topElf() {
        assertEquals(24000, topOne(parse(EXAMPLE_ONE)))
    }

    @Test
    fun topThreeElves() {
        assertEquals(45000, topThree(parse(EXAMPLE_ONE)))
    }

}
