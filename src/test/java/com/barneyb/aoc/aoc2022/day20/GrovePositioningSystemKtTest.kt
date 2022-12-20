package com.barneyb.aoc.aoc2022.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
1
2
-3
3
-2
0
4
"""

class GrovePositioningSystemKtTest {

    @Test
    fun parsing() {
        assertEquals(listOf<Long>(1, 2, -3, 3, -2, 0, 4), parse(EXAMPLE_ONE))
    }

    @Test
    fun sumOfCoords() {
        assertEquals(3, sumOfCoords(parse(EXAMPLE_ONE)))
    }

    @Test
    fun oneFullRound() {
        assertEquals(
            2434767459 + 1623178306 + 811589153,
            sumOfCoords(parse(EXAMPLE_ONE), DECRYPTION_KEY)
        )
    }

    @Test
    fun fullDecrypt() {
        assertEquals(1623178306, fullDecryption(parse(EXAMPLE_ONE)))
    }

}
