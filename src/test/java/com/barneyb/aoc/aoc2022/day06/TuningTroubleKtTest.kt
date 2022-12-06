package com.barneyb.aoc.aoc2022.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
    mjqjpqmgbljsphdztnvjfqwrcgsmlb
"""

class TuningTroubleKtTest {

    @Test
    fun parse() {
        assertEquals("mjqjpqmgbljsphdztnvjfqwrcgsmlb", parse(EXAMPLE_ONE))
    }

    @Test
    fun startOfSignal() {
        assertEquals(7, endOfStartPacketMarker(parse(EXAMPLE_ONE)))
        assertEquals(
            5,
            endOfStartPacketMarker(parse("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        )
        assertEquals(
            6,
            endOfStartPacketMarker(parse("nppdvjthqldpwncqszvftbrmjlhg"))
        )
        assertEquals(
            10,
            endOfStartPacketMarker(parse("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        )
        assertEquals(
            11,
            endOfStartPacketMarker(parse("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
        )
    }
}
