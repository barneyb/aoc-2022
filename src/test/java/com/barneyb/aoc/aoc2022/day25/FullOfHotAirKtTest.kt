package com.barneyb.aoc.aoc2022.day25

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122
"""

class FullOfHotAirKtTest {

    @Test
    fun parseSingle() {
        listOf(
            1L to "1",
            2L to "2",
            3L to "1=",
            4L to "1-",
            5L to "10",
            6L to "11",
            7L to "12",
            8L to "2=",
            9L to "2-",
            10L to "20",
            15L to "1=0",
            20L to "1-0",
            2022L to "1=11-2",
            12345L to "1-0---0",
            314159265L to "1121-1110-1=0",
        ).forEach { (d, s) ->
            assertEquals(d, s.fromSnafu(), "parsing '$s'")
            assertEquals(s, d.toSnafu(), "formatting '$d'")
        }
    }

    @Test
    fun exampleOne() {
        assertEquals("2=-1=0", totalFuel(parse(EXAMPLE_ONE)))
    }

}
