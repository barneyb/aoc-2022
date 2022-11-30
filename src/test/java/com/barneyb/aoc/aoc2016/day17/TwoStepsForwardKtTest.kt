package com.barneyb.aoc.aoc2016.day17

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TwoStepsForwardKtTest {

    @Test
    fun exampleOne() {
        assertEquals("DDRRRD", shortestPath("ihgpwlah"))
    }

    @Test
    fun exampleTwo() {
        assertEquals("DDUDRLRRUDRD", shortestPath("kglvqrro"))
    }

    @Test
    fun exampleThree() {
        assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", shortestPath("ulqzkmiv"))
    }

    @Test
    fun doorChecks() {
        assertAll(
            {
                assertArrayEquals(
                    booleanArrayOf(true, true, true, false),
                    doors("hijkl")
                )
            },
            {
                assertArrayEquals(
                    booleanArrayOf(false, false, false, false),
                    doors("hijkl", "DR")
                )
            })
    }

    @Test
    fun adjacent() {
        val start = State.start("hijkl")
        val adj = adjacent(start)
        assertEquals(1, adj.size)
        assertEquals(start.down(), adj.first())
    }

}
