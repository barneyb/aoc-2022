package com.barneyb.aoc.aoc2022.day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

private const val EXAMPLE_ONE = """
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
"""

class DistressSignalKtTest {

    @Test
    fun parsing() {
        assertAll(
            { assertEquals(listOf(1, 1, 3, 1, 1), parseList("[1,1,3,1,1]")) },
            { assertEquals(listOf(listOf(1), 4), parseList("[[1],4]")) },
            {
                assertEquals(
                    listOf(listOf(emptyList<Int>())),
                    parseList("[[[]]]")
                )
            },
            {
                assertEquals(
                    listOf(
                        1,
                        listOf(2, listOf(3, listOf(4, listOf(5, 6, 7)))),
                        8,
                        9
                    ),
                    parseList("[1,[2,[3,[4,[5,6,7]]]],8,9]")
                )
            },
        )
    }

    @Test
    fun comparisons() {
        assertEquals(-1, comparePackets(listOf(1), listOf(2)))
        assertEquals(0, comparePackets(listOf(1), listOf(1)))
        assertEquals(1, comparePackets(listOf(2), listOf(1)))
        assertEquals(-1, comparePackets(listOf(1), listOf(1, 2)))
        assertEquals(1, comparePackets(listOf(1, 2), listOf(1)))
        assertEquals(-1, comparePackets(listOf(listOf(0, 0, 0)), listOf(2)))
    }

    @Test
    fun pairTwo() {
        assertEquals(
            -1,
            comparePackets(
                listOf(listOf(1), listOf(2, 3, 4)),
                listOf(listOf(1), 4),
            )
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(13, partOne(parse(EXAMPLE_ONE)))
    }
}
