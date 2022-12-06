package com.barneyb.aoc.aoc2022.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
    mjqjpqmgbljsphdztnvjfqwrcgsmlb
"""

private const val EXAMPLE_TWO = "bvwbjplbgvbhsrlpgdmjqwftvncz"

private const val EXAMPLE_THREE = "nppdvjthqldpwncqszvftbrmjlhg"

private const val EXAMPLE_FOUR = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"

private const val EXAMPLE_FIVE = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"

class TuningTroubleKtTest {

    @Test
    fun parse() {
        assertEquals("mjqjpqmgbljsphdztnvjfqwrcgsmlb", parse(EXAMPLE_ONE))
    }

    @Test
    fun startOfSignal() {
        assertEquals(7, endOfStartPacketMarker(parse(EXAMPLE_ONE)))
        assertEquals(5, endOfStartPacketMarker(parse(EXAMPLE_TWO)))
        assertEquals(6, endOfStartPacketMarker(parse(EXAMPLE_THREE)))
        assertEquals(10, endOfStartPacketMarker(parse(EXAMPLE_FOUR)))
        assertEquals(11, endOfStartPacketMarker(parse(EXAMPLE_FIVE)))
    }

    @Test
    fun startOfMessage() {
        assertEquals(19, endOfStartMessageMarker(parse(EXAMPLE_ONE)))
        assertEquals(23, endOfStartMessageMarker(parse(EXAMPLE_TWO)))
        assertEquals(23, endOfStartMessageMarker(parse(EXAMPLE_THREE)))
        assertEquals(29, endOfStartMessageMarker(parse(EXAMPLE_FOUR)))
        assertEquals(26, endOfStartMessageMarker(parse(EXAMPLE_FIVE)))
    }
}
