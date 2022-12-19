package com.barneyb.aoc.aoc2022.day19

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StepTest {

    @Test
    fun gues() {
        println((1..30).sumOf { it * 10 })
    }

    @Test
    fun bunchaSteps() {
        Assertions.assertEquals(
            Step(13, intArrayOf(2, 1, 1, 1), intArrayOf(5, 3, 1, 3)),
            Step(10, intArrayOf(1, 1, 1, 1), intArrayOf(5, 0, 10, 0))
                .build(intArrayOf(3, 0, 12, 0), intArrayOf(1, 0, 0, 0))
        )
        Assertions.assertEquals(
            Step(11, intArrayOf(2, 1, 1, 1), intArrayOf(3, 1, 4, 1)),
            Step(10, intArrayOf(1, 1, 1, 1), intArrayOf(5, 0, 15, 0))
                .build(intArrayOf(3, 0, 12, 0), intArrayOf(1, 0, 0, 0))
        )
        Assertions.assertEquals(
            null,
            Step(10, intArrayOf(0, 1, 0, 1), intArrayOf(2, 0, 15, 0))
                .build(intArrayOf(3, 0, 12, 0), intArrayOf(1, 0, 0, 0))
        )
        Assertions.assertEquals(
            Step(2, intArrayOf(11, 10, 10, 10), intArrayOf(14, 13, 12, 15)),
            Step(1, intArrayOf(10, 10, 10, 10), intArrayOf(5, 5, 5, 5))
                .build(intArrayOf(1, 2, 3, 0), intArrayOf(1, 0, 0, 0))
        )
        Assertions.assertEquals(
            Step(4, intArrayOf(4, 3, 3, 3), intArrayOf(4, 9, 9, 9)),
            Step(1, intArrayOf(3, 3, 3, 3), intArrayOf(0, 0, 0, 0))
                .build(intArrayOf(5, 0, 0, 0), intArrayOf(1, 0, 0, 0))
        )
        Assertions.assertEquals(
            Step(5, intArrayOf(1, 2, 0, 0), intArrayOf(1, 2, 0, 0)),
            Step(3, intArrayOf(1, 1, 0, 0), intArrayOf(1, 0, 0, 0))
                .build(intArrayOf(2, 0, 0, 0), intArrayOf(0, 1, 0, 0))
        )
    }

    @Test
    fun partiallyAcquired() {
        val start = Step(5, intArrayOf(2, 0, 0, 0), intArrayOf(1, 0, 0, 0))
        val actual = start
            .build(intArrayOf(2, 0, 0, 0), intArrayOf(0, 1, 0, 0))
        val expected =
            Step(7, intArrayOf(2, 1, 0, 0), intArrayOf(3, 0, 0, 0))
        Assertions.assertEquals(expected, actual)
    }
}
